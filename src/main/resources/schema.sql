--이 파일 왜 만들어야 했는지 아직 전혀 모름

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
--아직 못해서 users_squad 이거 만들어야
(
    customerId bigint not null auto_increment,
    userId varchar(16) not null,
    password varchar(32) not null,
    name varchar(16) not null,
    email varchar(64) not null,
    primary key (userId)
);
