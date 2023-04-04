drop table if exists users CASCADE;
CREATE TABLE users
(
    id       bigint auto_increment,
    userId   VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    name     VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL,
    primary key (id)
);