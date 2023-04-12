create table if not exists `user`
(
    id       bigint       not null auto_increment,
    user_id  varchar(255) not null,
    password varchar(255) not null,
    name     varchar(255) not null,
    email    varchar(255) not null,
    primary key (id)
);

create table if not exists post
(
    id           bigint       not null auto_increment,
    writer       varchar(255) not null,
    title        varchar(255) not null,
    contents     varchar(255) not null,
    writing_time datetime not null,
    primary key (id)
);