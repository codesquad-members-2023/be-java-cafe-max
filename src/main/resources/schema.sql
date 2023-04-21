create table if not exists article
(
    id         bigint       not null auto_increment,
    writer     varchar(64)  not null,
    title      varchar(64)  not null,
    contents   varchar(255) not null,
    created_at datetime     not null,
    primary key (id)
);

create table if not exists user_account
(
    user_id  varchar(64) not null,
    password varchar(64) not null,
    name     varchar(64) not null,
    email    varchar(64) not null,
    primary key (user_id)
);

create table if not exists article_comment
(
    id         bigint       not null auto_increment,
    user_id    varchar(64)  not null,
    contents   varchar(255) not null,
    created_at datetime     not null,
    article_id varchar(64)  not null,
    primary key (id)
);
