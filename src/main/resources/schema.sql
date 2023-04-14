create table if not exists article
(
    id         bigint   not null auto_increment,
    writer     varchar  not null,
    title      varchar  not null,
    contents   varchar  not null,
    created_at datetime not null,
    primary key (id)
);

create table if not exists user_account
(
    user_id  varchar not null,
    password varchar not null,
    name     varchar not null,
    email    varchar not null,
    primary key (user_id)
);
