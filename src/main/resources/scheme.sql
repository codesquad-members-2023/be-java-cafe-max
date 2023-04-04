drop table if exists users CASCADE;
create table users
(
    sequence bigint NOT NULL AUTO_INCREMENT,
    userId   varchar(10),
    password varchar(10),
    name varchar(10),
    email varchar(320),
    primary key (userId)
);

drop table if exists articles CASCADE;
create table articles
(
    sequence bigint NOT NULL AUTO_INCREMENT,
    writer   varchar(10),
    title varchar(10),
    contents text(1000),
    primary key (writer)
);
