-- 1) Create database and use it
CREATE DATABASE IF NOT EXISTS devspark CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE devspark;

-- 2) Drop old tables if they exist (optional reset)
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS votes;
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS news;
DROP TABLE IF EXISTS users;
SET FOREIGN_KEY_CHECKS = 1;

-- 3) Create tables (aligned with JPA entities: numeric IDs, audit fields, enums)

-- users
CREATE TABLE users (
  id BIGINT NOT NULL AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL UNIQUE,
  email VARCHAR(100) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  first_name VARCHAR(50),
  last_name VARCHAR(50),
  is_active BOOLEAN DEFAULT TRUE,
  role ENUM('READER','ADMIN','USER') DEFAULT 'USER',
  created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  updated_at DATETIME(6) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(6),
  version BIGINT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB;

-- news (with embedded voteCount: fake_votes, not_fake_votes)
CREATE TABLE news (
  id BIGINT NOT NULL AUTO_INCREMENT,
  title VARCHAR(255) NOT NULL,
  short_detail VARCHAR(500) NOT NULL,
  full_detail TEXT NOT NULL,
  image_url VARCHAR(500),
  status ENUM('FAKE','NOT_FAKE','UNDECIDED') DEFAULT 'UNDECIDED',
  reporter_id BIGINT NOT NULL,
  soft_deleted BOOLEAN DEFAULT FALSE,
  fake_votes INT DEFAULT 0,
  not_fake_votes INT DEFAULT 0,
  created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  updated_at DATETIME(6) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(6),
  version BIGINT NULL,
  PRIMARY KEY (id),
  KEY idx_news_reporter (reporter_id),
  CONSTRAINT fk_news_reporter FOREIGN KEY (reporter_id) REFERENCES users(id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;

-- comments
CREATE TABLE comments (
  id BIGINT NOT NULL AUTO_INCREMENT,
  news_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  text TEXT NOT NULL,
  image_url VARCHAR(500),
  vote_type ENUM('FAKE','NOT_FAKE') NULL,
  soft_deleted BOOLEAN DEFAULT FALSE,
  created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  updated_at DATETIME(6) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(6),
  version BIGINT NULL,
  PRIMARY KEY (id),
  KEY idx_comments_news (news_id),
  KEY idx_comments_user (user_id),
  CONSTRAINT fk_comments_news FOREIGN KEY (news_id) REFERENCES news(id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_comments_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;

-- votes (unique one vote per user per news)
CREATE TABLE votes (
  id BIGINT NOT NULL AUTO_INCREMENT,
  news_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  vote_type ENUM('FAKE','NOT_FAKE') NOT NULL,
  created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  updated_at DATETIME(6) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(6),
  version BIGINT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uk_vote_once (news_id, user_id),
  KEY idx_votes_news (news_id),
  KEY idx_votes_user (user_id),
  CONSTRAINT fk_votes_news FOREIGN KEY (news_id) REFERENCES news(id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_votes_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;

-- 4) Seed basic data

-- users
INSERT INTO users (username, email, password, first_name, last_name, role, is_active)
VALUES
  ('admin', 'admin@demo.com', 'admin', 'Admin', 'User', 'ADMIN', TRUE),
  ('reporter1', 'reporter1@demo.com', 'pass', 'Reporter', 'One', 'USER', TRUE),
  ('reader1', 'reader1@demo.com', 'pass', 'Reader', 'One', 'READER', TRUE)
ON DUPLICATE KEY UPDATE email=VALUES(email);

-- get ids for seeds
SET @admin_id = (SELECT id FROM users WHERE username='admin' LIMIT 1);
SET @reporter_id = (SELECT id FROM users WHERE username='reporter1' LIMIT 1);
SET @reader_id = (SELECT id FROM users WHERE username='reader1' LIMIT 1);

-- news
INSERT INTO news (title, short_detail, full_detail, image_url, status, reporter_id, soft_deleted, fake_votes, not_fake_votes)
VALUES
  ('Aliens Landed in Bangkok', 'A mysterious light was seen over the Grand Palace.', 'Full details of the event...', '/images/image1.jpg', 'FAKE', @reporter_id, FALSE, 5, 1),
  ('Bangkok Floods Reach New Record', 'Chao Phraya River overflowed in multiple districts.', 'Extensive flood report...', '/images/image2.jpg', 'NOT_FAKE', @reporter_id, FALSE, 1, 6)
ON DUPLICATE KEY UPDATE title=VALUES(title);

SET @news1 = (SELECT id FROM news WHERE title='Aliens Landed in Bangkok' LIMIT 1);
SET @news2 = (SELECT id FROM news WHERE title='Bangkok Floods Reach New Record' LIMIT 1);

-- votes
INSERT INTO votes (news_id, user_id, vote_type)
VALUES
  (@news1, @admin_id, 'FAKE')
ON DUPLICATE KEY UPDATE vote_type=VALUES(vote_type);

-- comments
INSERT INTO comments (news_id, user_id, text, vote_type)
VALUES
  (@news1, @admin_id, 'Looks photoshopped!', 'FAKE')
;

-- 5) Optional: quick stats sanity check
SELECT
  (SELECT COUNT(*) FROM users)   AS users_count,
  (SELECT COUNT(*) FROM news)    AS news_count,
  (SELECT COUNT(*) FROM votes)   AS votes_count,
  (SELECT COUNT(*) FROM comments) AS comments_count;