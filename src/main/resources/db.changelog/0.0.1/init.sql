CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(32) NOT NULL,
    email VARCHAR(32) NOT NULL
);

COMMENT ON COLUMN users.id IS 'ИД пользователя';
COMMENT ON COLUMN users.username IS 'Логин пользователя';
COMMENT ON COLUMN users.email IS 'Почтовый адрес пользователя';

CREATE TABLE posts (
    id BIGSERIAL PRIMARY KEY,
    text VARCHAR(256) NOT NULL,
    user_id BIGINT REFERENCES users(id)
);

COMMENT ON COLUMN posts.id IS 'ИД публикации';
COMMENT ON COLUMN posts.text IS 'Текст публикации';
COMMENT ON COLUMN posts.user_id IS 'ИД автора';

CREATE TABLE comments (
    id BIGSERIAL PRIMARY KEY,
    text VARCHAR(128) NOT NULL,
    user_id BIGINT REFERENCES users(id),
    post_id BIGINT REFERENCES posts(id)
);

COMMENT ON COLUMN comments.id IS 'ИД комментария';
COMMENT ON COLUMN comments.text IS 'Текст комментария';
COMMENT ON COLUMN comments.user_id IS 'ИД автора';
COMMENT ON COLUMN comments.post_id IS 'ИД поста';
