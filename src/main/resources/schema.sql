create table if not exists articles
(
    id bigint not null auto_increment,
    writer varchar(16) not null,
    title varchar(32) not null,
    contents varchar(255) not null,
    createdAt datetime not null,
    points bigint not null,
    primary key (id)
);

create table if not exists users
(
    customerId bigint not null auto_increment,
    userId varchar(16) not null,
    password varchar(32) not null,
    name varchar(16) not null,
    email varchar(64) not null,
    primary key (userId)
);


