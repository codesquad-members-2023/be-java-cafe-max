DROP TABLE IF EXISTS users;
CREATE TABLE users(
    user_id VARCHAR,
    password VARCHAR NOT NULL,
    user_name VARCHAR NOT NULL UNIQUE,
    email VARCHAR NOT NULL,
    PRIMARY KEY (user_id)
);

DROP TABLE IF EXISTS post;
CREATE TABLE post(
    post_id BIGINT AUTO_INCREMENT,
    writer VARCHAR(50) NOT NULL,
    title VARCHAR(255) NOT NULL,
    contents VARCHAR(255) NOT NULL,
    write_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (post_id),
    FOREIGN KEY (writer) REFERENCES users(user_name)
);

DROP TABLE IF EXISTS comment;
CREATE TABLE comment(
    comment_id BIGINT AUTO_INCREMENT,
    post_id BIGINT NOT NULL,
    writer VARCHAR(50) NOT NULL,
    contents VARCHAR(255) NOT NULL,
    write_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (comment_id),
    FOREIGN KEY (post_id) REFERENCES post(post_id),
    FOREIGN KEY (writer) REFERENCES users(user_name)
);
