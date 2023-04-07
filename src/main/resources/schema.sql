DROP TABLE IF EXISTS post;
CREATE TABLE post(
    post_id BIGINT AUTO_INCREMENT,
    writer VARCHAR(50) NOT NULL,
    title VARCHAR(255) NOT NULL,
    contents VARCHAR(255) NOT NULL,
    write_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (post_id)
);

DROP TABLE IF EXISTS users;
CREATE TABLE users(
    user_id VARCHAR,
    password VARCHAR NOT NULL,
    user_name VARCHAR NOT NULL,
    email VARCHAR NOT NULL,
    PRIMARY KEY (user_id)
);
