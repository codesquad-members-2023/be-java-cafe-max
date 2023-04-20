DROP TABLE IF EXISTS reply;
DROP TABLE IF EXISTS article;
DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    sequence bigint NOT NULL AUTO_INCREMENT,
    userId varchar(10) NOT NULL,
    password varchar(10) NOT NULL,
    name varchar(10) NOT NULL,
    email varchar(320) NOT NULL,
    primary key (sequence, userId)
    );

ALTER TABLE users ADD INDEX(userId);

CREATE TABLE article
(
    id bigint NOT NULL AUTO_INCREMENT,
    writer varchar(10) NOT NULL,
    title varchar(10) NOT NULL,
    contents text(1000) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (writer)
    REFERENCES users (userId) ON UPDATE CASCADE
    );

CREATE TABLE reply
(
    id bigint NOT NULL AUTO_INCREMENT,
    article_id bigint NOT NULL,
    users_id varchar(10) NOT NULL,
    contents text(1000) NOT NULL,
    create_dateTime DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (article_id)
    REFERENCES article (id) ON UPDATE CASCADE,
    FOREIGN KEY (users_id)
    REFERENCES users (userId) ON UPDATE CASCADE
    );
