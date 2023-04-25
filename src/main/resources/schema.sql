DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS post;
DROP TABLE IF EXISTS users;

CREATE TABLE users(
    user_id VARCHAR(50),
    password VARCHAR(50) NOT NULL,
    user_name VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE post(
    post_id BIGINT AUTO_INCREMENT,
    writer VARCHAR(50) NOT NULL,
    title VARCHAR(255) NOT NULL,
    contents VARCHAR(2000) NOT NULL,
    write_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (post_id),
    FOREIGN KEY (writer) REFERENCES users(user_name)
    ON UPDATE CASCADE
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE comment(
    comment_id BIGINT AUTO_INCREMENT,
    post_id BIGINT NOT NULL,
    writer VARCHAR(50) NOT NULL,
    contents VARCHAR(2000) NOT NULL,
    write_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (comment_id),
    FOREIGN KEY (post_id) REFERENCES post(post_id),
    FOREIGN KEY (writer) REFERENCES users(user_name)
    ON UPDATE CASCADE
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
