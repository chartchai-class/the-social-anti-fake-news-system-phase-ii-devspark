-- Supabase Database Schema for Anti-Fake News System
-- Run this SQL in your Supabase SQL Editor

-- Enable UUID extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Profiles Table (extends Supabase auth.users)
CREATE TABLE IF NOT EXISTS profiles (
  id UUID PRIMARY KEY REFERENCES auth.users(id) ON DELETE CASCADE,
  username VARCHAR(50) UNIQUE NOT NULL,
  email VARCHAR(100) UNIQUE NOT NULL,
  first_name VARCHAR(50),
  last_name VARCHAR(50),
  full_name VARCHAR(100),
  image_url TEXT,
  role VARCHAR(20) DEFAULT 'READER' CHECK (role IN ('READER', 'MEMBER', 'ADMIN')),
  is_active BOOLEAN DEFAULT true,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
  updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- News Table
CREATE TABLE IF NOT EXISTS news (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  title VARCHAR(255) NOT NULL,
  short_detail VARCHAR(500) NOT NULL,
  full_detail TEXT NOT NULL,
  image_url TEXT,
  reporter_id UUID NOT NULL REFERENCES profiles(id) ON DELETE CASCADE,
  status VARCHAR(20) DEFAULT 'UNDECIDED' CHECK (status IN ('FAKE', 'NOT_FAKE', 'UNDECIDED')),
  soft_deleted BOOLEAN DEFAULT false,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
  updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Comments Table
CREATE TABLE IF NOT EXISTS comments (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  news_id UUID NOT NULL REFERENCES news(id) ON DELETE CASCADE,
  user_id UUID NOT NULL REFERENCES profiles(id) ON DELETE CASCADE,
  text TEXT NOT NULL,
  image_url TEXT,
  vote_type VARCHAR(20) CHECK (vote_type IN ('FAKE', 'NOT_FAKE')),
  parent_comment_id UUID REFERENCES comments(id) ON DELETE CASCADE,
  soft_deleted BOOLEAN DEFAULT false,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
  updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Votes Table
CREATE TABLE IF NOT EXISTS votes (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  news_id UUID NOT NULL REFERENCES news(id) ON DELETE CASCADE,
  user_id UUID NOT NULL REFERENCES profiles(id) ON DELETE CASCADE,
  vote_type VARCHAR(20) NOT NULL CHECK (vote_type IN ('FAKE', 'NOT_FAKE')),
  created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
  UNIQUE(news_id, user_id) -- One vote per user per news
);

-- Indexes for performance
CREATE INDEX IF NOT EXISTS idx_news_reporter_id ON news(reporter_id);
CREATE INDEX IF NOT EXISTS idx_news_status ON news(status);
CREATE INDEX IF NOT EXISTS idx_news_soft_deleted ON news(soft_deleted);
CREATE INDEX IF NOT EXISTS idx_news_created_at ON news(created_at DESC);

CREATE INDEX IF NOT EXISTS idx_comments_news_id ON comments(news_id);
CREATE INDEX IF NOT EXISTS idx_comments_user_id ON comments(user_id);
CREATE INDEX IF NOT EXISTS idx_comments_soft_deleted ON comments(soft_deleted);

CREATE INDEX IF NOT EXISTS idx_votes_news_id ON votes(news_id);
CREATE INDEX IF NOT EXISTS idx_votes_user_id ON votes(user_id);

-- Row Level Security (RLS) Policies

-- Enable RLS
ALTER TABLE profiles ENABLE ROW LEVEL SECURITY;
ALTER TABLE news ENABLE ROW LEVEL SECURITY;
ALTER TABLE comments ENABLE ROW LEVEL SECURITY;
ALTER TABLE votes ENABLE ROW LEVEL SECURITY;

-- Profiles Policies
CREATE POLICY "Users can view all profiles"
  ON profiles FOR SELECT
  USING (true);

CREATE POLICY "Users can update own profile"
  ON profiles FOR UPDATE
  USING (auth.uid() = id);

CREATE POLICY "Users can insert own profile"
  ON profiles FOR INSERT
  WITH CHECK (auth.uid() = id);

-- News Policies
CREATE POLICY "Everyone can view non-deleted news"
  ON news FOR SELECT
  USING (soft_deleted = false OR auth.uid() IN (
    SELECT id FROM profiles WHERE role = 'ADMIN'
  ));

CREATE POLICY "Members and Admins can create news"
  ON news FOR INSERT
  WITH CHECK (
    auth.uid() IN (
      SELECT id FROM profiles WHERE role IN ('MEMBER', 'ADMIN')
    )
  );

CREATE POLICY "Only admins can update news"
  ON news FOR UPDATE
  USING (
    auth.uid() IN (
      SELECT id FROM profiles WHERE role = 'ADMIN'
    )
  );

-- Comments Policies
CREATE POLICY "Everyone can view non-deleted comments"
  ON comments FOR SELECT
  USING (soft_deleted = false OR auth.uid() IN (
    SELECT id FROM profiles WHERE role = 'ADMIN'
  ));

CREATE POLICY "Authenticated users can create comments"
  ON comments FOR INSERT
  WITH CHECK (auth.uid() = user_id);

CREATE POLICY "Users can update own comments"
  ON comments FOR UPDATE
  USING (auth.uid() = user_id);

CREATE POLICY "Only admins can soft delete comments"
  ON comments FOR UPDATE
  USING (
    auth.uid() IN (
      SELECT id FROM profiles WHERE role = 'ADMIN'
    )
  );

-- Votes Policies
CREATE POLICY "Everyone can view votes"
  ON votes FOR SELECT
  USING (true);

CREATE POLICY "Authenticated users can create votes"
  ON votes FOR INSERT
  WITH CHECK (auth.uid() = user_id);

CREATE POLICY "Users can update own votes"
  ON votes FOR UPDATE
  USING (auth.uid() = user_id);

-- Functions for automatic timestamps
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
  NEW.updated_at = NOW();
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Triggers for updated_at
CREATE TRIGGER update_profiles_updated_at
  BEFORE UPDATE ON profiles
  FOR EACH ROW
  EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_news_updated_at
  BEFORE UPDATE ON news
  FOR EACH ROW
  EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_comments_updated_at
  BEFORE UPDATE ON comments
  FOR EACH ROW
  EXECUTE FUNCTION update_updated_at_column();

-- Function to automatically create profile on user signup
CREATE OR REPLACE FUNCTION public.handle_new_user()
RETURNS TRIGGER AS $$
BEGIN
  INSERT INTO public.profiles (id, email, username)
  VALUES (
    NEW.id,
    NEW.email,
    COALESCE(NEW.raw_user_meta_data->>'username', split_part(NEW.email, '@', 1))
  );
  RETURN NEW;
END;
$$ LANGUAGE plpgsql SECURITY DEFINER;

-- Trigger to create profile when user signs up
CREATE TRIGGER on_auth_user_created
  AFTER INSERT ON auth.users
  FOR EACH ROW
  EXECUTE FUNCTION public.handle_new_user();

-- Function to recalculate news status based on votes
CREATE OR REPLACE FUNCTION recalculate_news_status(p_news_id UUID)
RETURNS VARCHAR(20) AS $$
DECLARE
  v_fake_count INTEGER;
  v_not_fake_count INTEGER;
  v_status VARCHAR(20);
BEGIN
  SELECT 
    COUNT(*) FILTER (WHERE vote_type = 'FAKE'),
    COUNT(*) FILTER (WHERE vote_type = 'NOT_FAKE')
  INTO v_fake_count, v_not_fake_count
  FROM votes
  WHERE news_id = p_news_id;
  
  IF v_fake_count > v_not_fake_count THEN
    v_status := 'FAKE';
  ELSIF v_not_fake_count > v_fake_count THEN
    v_status := 'NOT_FAKE';
  ELSE
    v_status := 'UNDECIDED';
  END IF;
  
  UPDATE news
  SET status = v_status
  WHERE id = p_news_id;
  
  RETURN v_status;
END;
$$ LANGUAGE plpgsql;

