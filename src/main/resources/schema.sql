drop table if exists `user`;
create table `user`
(
    id       bigint       not null auto_increment,
    user_id  varchar(255) not null,
    password varchar(255) not null,
    name     varchar(255) not null,
    email    varchar(255) not null,
    primary key (id)
);

drop table if exists post;
create table post
(
    id           bigint       not null auto_increment,
    writer_id    varchar(255) not null,
    writer_name  varchar(255) not null,
    title        varchar(255) not null,
    contents     varchar(255) not null,
    writing_time timestamp(9) not null,
    deleted      boolean default false,
    primary key (id)
);