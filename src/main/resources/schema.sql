-- 이것은 DDL

create table if not exists articleTable
(
    id bigint not null auto_increment,
    writer varchar(16) not null,
    title varchar(32) not null,
    contents varchar(255) not null,
    createdTime datetime not null,
    primary key (id)
);

create table if not exists userTable
(
    id bigint not null auto_increment,
    userId varchar(16) not null,
    password varchar(32) not null,
    email varchar(64) not null,
    primary key (id)
);
