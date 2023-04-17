DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS article;

CREATE TABLE users
(
    id       varchar(200) not null,
    password varchar(200) not null,
    name     varchar(200) not null,
    email    varchar(200) not null,
    PRIMARY KEY (id)
);

CREATE TABLE article
(
    id        bigint auto_increment,
    writer    varchar(200) not null,
    title     varchar(200) not null,
    contents  text         not null,
    createdAt datetime,
    writer_id varchar(200) not null,
    PRIMARY KEY (id),
    FOREIGN KEY (writer_id) REFERENCES users (id) ON UPDATE CASCADE
);
