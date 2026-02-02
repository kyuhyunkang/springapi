-- users
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL,
    CONSTRAINT uk_users_username UNIQUE (username)
);

-- follows
CREATE TABLE follows (
    id BIGSERIAL PRIMARY KEY,
    follower_id BIGINT NOT NULL,
    following_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL,
    CONSTRAINT uk_follows_follower_following UNIQUE (follower_id, following_id),
    CONSTRAINT fk_follows_follower FOREIGN KEY (follower_id) REFERENCES users (id),
    CONSTRAINT fk_follows_following FOREIGN KEY (following_id) REFERENCES users (id)
);

CREATE INDEX idx_follows_following_id ON follows (following_id);

-- follow_counts
CREATE TABLE follow_counts (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    followers_count BIGINT NOT NULL DEFAULT 0,
    followees_count BIGINT NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL,
    CONSTRAINT uk_follow_counts_user_id UNIQUE (user_id),
    CONSTRAINT fk_follow_counts_user FOREIGN KEY (user_id) REFERENCES users (id)
);

-- likes
CREATE TABLE likes (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    post_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL,
    CONSTRAINT uk_likes_user_post UNIQUE (user_id, post_id)
);

CREATE INDEX idx_likes_post_id ON likes (post_id);
