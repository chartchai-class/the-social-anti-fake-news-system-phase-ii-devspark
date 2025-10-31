# Supabase Setup Guide

This guide will help you set up Supabase for the Anti-Fake News System.

## Step 1: Create Supabase Project

1. Go to [https://supabase.com](https://supabase.com)
2. Sign up or log in
3. Click "New Project"
4. Fill in:
   - Project name: `anti-fake-news` (or your choice)
   - Database password: (save this securely)
   - Region: Choose closest to you
5. Click "Create new project"

## Step 2: Set Up Database Schema

Use the Postgres-compatible schema for Supabase.

1. In your Supabase dashboard, go to **SQL Editor**
2. Open and run `social-anti-fake-news/supabase-schema-postgres.sql`
3. Click "Run" to execute the schema (it's idempotent)
4. Verify tables are created: Go to **Table Editor** and check for:
   - `profiles`
   - `news`
   - `comments`
   - `votes`

Note: The older `supabase-schema.sql` file is MySQL-style and not for Supabase. Use `supabase-schema-postgres.sql` instead.

## Step 3: Get API Credentials

1. Go to **Settings** → **API**
2. Copy the following:
   - **Project URL** (e.g., `https://xxxxx.supabase.co`)
   - **anon/public key** (starts with `eyJ...`)

## Step 4: Configure Environment Variables

1. In your frontend directory, create a `.env` file:

```bash
cd social-anti-fake-news/frontend
```

2. Create `.env` file with:

```env
VITE_SUPABASE_URL=https://your-project-id.supabase.co
VITE_SUPABASE_ANON_KEY=your-anon-key-here
```

3. **Important**: Add `.env` to `.gitignore` to keep credentials safe

## Step 5: Install Dependencies

```bash
cd social-anti-fake-news/frontend
npm install @supabase/supabase-js
```

## Step 6: Verify Setup

1. Start your development server:
```bash
npm run dev
```

2. Try registering a new user
3. Check Supabase Dashboard → **Authentication** → **Users** to see the new user
4. Check **Table Editor** → **profiles** to see the profile record

## Step 7: Configure Email (Optional)

For production, configure email settings:
1. Go to **Settings** → **Auth**
2. Configure SMTP settings or use Supabase's built-in email
3. Set up email templates for:
   - Email confirmation
   - Password reset
   - Magic link login

## Database Structure

### Tables Created:

1. **profiles** - User profiles (extends auth.users)
   - Links to Supabase auth
   - Stores: username, name, surname, email, image_url, role

2. **news** - News articles
   - Stores: title, short_detail, full_detail, image_url, status, reporter_id

3. **comments** - User comments on news
   - Stores: text, image_url, vote_type, user_id, news_id

4. **votes** - User votes on news
   - Stores: vote_type (FAKE/NOT_FAKE), user_id, news_id

### Relationships:

- User (profiles) → News (One-to-Many)
- News → Comments (One-to-Many)
- News → Votes (One-to-Many)
- User → Comments (One-to-Many)
- User → Votes (One-to-Many)

## Row Level Security (RLS)

All tables have RLS enabled with policies:
- **Profiles**: Users can view all, update own
- **News**: Everyone can view (non-deleted), Members/Admins can create, Admins can update
- **Comments**: Everyone can view (non-deleted), authenticated users can create
- **Votes**: Everyone can view, authenticated users can create/update own

## Testing

### Create Test Users:

You can create test users via Supabase Dashboard:
1. Go to **Authentication** → **Users**
2. Click "Add user" → "Create new user"
3. Set email and password
4. Go to **Table Editor** → **profiles**
5. Update the user's role (READER, MEMBER, or ADMIN)

### Test Data:

Use the SQL Editor to insert test data:

```sql
-- Insert test news (replace reporter_id with actual user ID)
INSERT INTO news (title, short_detail, full_detail, reporter_id, status)
VALUES (
  'Test News Article',
  'This is a short summary',
  'This is the full article content...',
  'YOUR_USER_ID_HERE',
  'UNDECIDED'
);
```

## Troubleshooting

### Issue: "relation does not exist"
**Solution**: Make sure you ran the entire `supabase-schema.sql` file

### Issue: "permission denied"
**Solution**: Check RLS policies are correctly set up

### Issue: "duplicate key value"
**Solution**: Username or email already exists. Use different values.

### Issue: Environment variables not working
**Solution**: 
1. Make sure `.env` file is in `frontend/` directory
2. Restart dev server after changing `.env`
3. Variables must start with `VITE_` to be accessible in Vite

## Production Deployment

When deploying to Vercel:
1. Go to Vercel project settings
2. Add environment variables:
   - `VITE_SUPABASE_URL`
   - `VITE_SUPABASE_ANON_KEY`
3. Redeploy your application

## Security Notes

- The `anon` key is safe for client-side use (RLS protects data)
- Never commit `.env` file to Git
- Use Supabase's built-in auth features for security
- Enable email confirmation in production

## Support

For Supabase documentation: https://supabase.com/docs

