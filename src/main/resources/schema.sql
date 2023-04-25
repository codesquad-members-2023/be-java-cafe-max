DROP TABLE IF EXISTS reply;
DROP TABLE IF EXISTS article;
DROP TABLE IF EXISTS users;


CREATE TABLE users
(
    id       varchar(200) not null,
    password varchar(200) not null,
    name     varchar(200) not null,
    email    varchar(255) not null,
    PRIMARY KEY (id)
);

CREATE TABLE article
(
    id        bigint auto_increment,
    title     varchar(200) not null,
    contents  text         not null,
    createdAt datetime,
    writer_id varchar(200) not null,
    PRIMARY KEY (id),
    FOREIGN KEY (writer_id) REFERENCES users (id) ON UPDATE CASCADE
);

CREATE TABLE reply
(
    id bigint auto_increment,
    contents text not null,
    createdAt datetime,
    user_id varchar(200) not null,
    article_id bigint not null,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE CASCADE,
    FOREIGN KEY (article_id) REFERENCES article (id) ON UPDATE CASCADE
);