-- 이것은 DDL

create table if not exists articles_squad
(
--    articleId bigint not null auto_increment,
    writer varchar(16) not null,
    title varchar(32) not null,
    contents varchar(255) not null,
    createdTime datetime not null,
    articleNum bigint not null auto_increment,
    primary key (articleNum)
);

create table if not exists users_squad
(
    userId bigint not null auto_increment,
    userNum bigint not null,
    userLoginId varchar(16) not null,
    password varchar(32) not null,
    email varchar(64) not null,
    primary key (userId)
);
