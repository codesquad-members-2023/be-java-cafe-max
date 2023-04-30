drop table if exists users;
drop table if exists article;
drop table if exists reply;

create table users
(
    customer_id bigint not null auto_increment,
    user_id varchar(16) not null,
    password varchar(32) not null,
    name varchar(16) not null,
    email varchar(64) not null,
    deleted varchar(1),
    primary key (customer_id)
);

create table article
(
    id bigint not null auto_increment,
    writer varchar(16) not null,
    title varchar(32) not null,
    contents varchar(255) not null,
    created_at datetime not null,
    modified_at datetime null,
    points bigint not null,
    deleted varchar(1),
    primary key (id)
);

create table reply
(
    reply_id bigint not null auto_increment,
    writer varchar(16) not null,
    contents varchar(255) not null,
    created_at datetime not null,
    modified_at datetime null,
    article_id bigint not null,
    deleted varchar(1),
    primary key (reply_id)
);



