DROP TABLE IF EXISTS users;
CREATE table users
(
    sequence bigint NOT NULL AUTO_INCREMENT,
    userId varchar(10),
    password varchar(10),
    name varchar(10),
    email varchar(320),
    primary key (userId)
);

DROP TABLE IF EXISTS article;
CREATE table article
(
    sequence bigint NOT NULL AUTO_INCREMENT,
    writer varchar(10),
    title varchar(10),
    contents text(1000),
    PRIMARY KEY (sequence),
    FOREIGN KEY (writer)
    REFERENCES users (userId) ON UPDATE CASCADE
);
