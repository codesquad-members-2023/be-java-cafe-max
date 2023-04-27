drop table if exists users;
drop table if exists articles;
drop table if exists replies;

create table users
(
    id       bigint auto_increment primary key,
    userId   varchar(20) not null,
    password varchar(30) not null,
    userName varchar(20) not null,
    email    varchar(30) not null
);

create table articles
(
    id          bigint auto_increment primary key,
    writer      varchar(20)   not null,
    title       varchar(30)   not null,
    contents    varchar(1000) not null,
    writtenTime timestamp     not null
);

create table replies
(
    id          bigint auto_increment primary key,
    articleId   bigint       not null,
    writer      varchar(20)  not null,
    contents    varchar(500) not null,
    writtenTime timestamp    not null
);

insert into users(userId, password, userName, email)
values ('tester', '1234', 'tester', 'email');

insert into articles(writer, title, contents, writtenTime)
values ('tester', 'title', 'this is from universe. S..t...a...y...', current_timestamp)