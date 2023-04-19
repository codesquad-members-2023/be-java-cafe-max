drop table if exists users;
drop table if exists articles;

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
    writer      varchar(20) not null,
    title       varchar(30) not null,
    contents    varchar(1000) not null,
    writtenTime timestamp not null
);