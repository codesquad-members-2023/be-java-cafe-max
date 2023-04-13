CREATE TABLE IF NOT EXISTS users
(
    sequence bigint NOT NULL AUTO_INCREMENT,
    userId varchar(10),
    password varchar(10),
    name varchar(10),
    email varchar(320),
    primary key (userId)
);

CREATE TABLE IF NOT EXISTS article
(
    sequence bigint NOT NULL AUTO_INCREMENT,
    writer varchar(10),
    title varchar(10),
    contents text(1000),
    PRIMARY KEY (sequence),
    FOREIGN KEY (writer)
    REFERENCES users (userId) ON UPDATE CASCADE
);
