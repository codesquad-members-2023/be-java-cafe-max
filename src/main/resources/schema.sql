-- DROP TABLE IF EXISTS reply;
-- DROP TABLE IF EXISTS article;
-- DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users
(
    userId          varchar(50),
    password        varchar(50) not null,
    name            varchar(50) not null,
    email           varchar(50) not null,
    primary key (userId)
);

CREATE TABLE IF NOT EXISTS article
(
    id              bigint auto_increment,
    userId          varchar(50) not null,
    writer          varchar(50) not null,
    title           varchar(255) not null,
    contents        varchar(255) not null,
    createdTime     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted         boolean DEFAULT FALSE not null,
    primary key (id)
);

CREATE TABLE IF NOT EXISTS reply
(
    id              bigint auto_increment,
    userId          varchar(50) not null,
    writer          varchar(50) not null,
    articleId       bigint not null,
    contents        varchar(255) not null,
    createdTime     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted         boolean DEFAULT FALSE not null,
    primary key (id),
    foreign key (articleId) references article(id)
);