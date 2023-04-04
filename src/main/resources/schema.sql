DROP TABLE IF EXISTS post;
DROP TABLE IF EXISTS users;
CREATE TABLE users
(
    user_id  INT       NOT NULl AUTO_INCREMENT,
    nickname CHAR(256) NOT NULL,
    email    CHAR(256) NOT NULL unique ,
    password CHAR(256) NOT NULL,
    PRIMARY KEY (user_id)
);

CREATE TABLE post
(
    post_id        INT       NOT NULL AUTO_INCREMENT,
    user_id        INT       NOT NULL,
    nickname       CHAR(256) NOT NULL,
    title          CHAR(256) NOT NULL,
    text_content   BLOB      NOT NULL,
    createDataTime DATETIME  NOT NULL,
    PRIMARY KEY (post_id),
    FOREIGN KEY (user_id) REFERENCES USERS (user_id)
)
