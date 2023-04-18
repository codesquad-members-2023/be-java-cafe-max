CREATE TABLE IF NOT EXISTS users
(
    sequence bigint NOT NULL AUTO_INCREMENT,
    userId varchar(10) NOT NULL,
    password varchar(10) NOT NULL,
    name varchar(10) NOT NULL,
    email varchar(320) NOT NULL,
    primary key (sequence, userId)
);

CREATE TABLE IF NOT EXISTS article
(
    sequence bigint NOT NULL AUTO_INCREMENT,
    writer varchar(10) NOT NULL,
    title varchar(10) NOT NULL,
    contents text(1000) NOT NULL,
    PRIMARY KEY (sequence),
    FOREIGN KEY (writer)
    REFERENCES users (userId) ON UPDATE CASCADE
);
