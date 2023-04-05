CREATE TABLE IF NOT EXISTS users
(
    userId       varchar(50),
    password     varchar(50) not null,
    name       varchar(50) not null,
    email        varchar(50) not null,
    primary key (userId)
);

CREATE TABLE IF NOT EXISTS article
(
    id           bigint auto_increment,
    writer       varchar(50) not null,
    title        varchar(255) not null,
    contents     varchar(255) not null,
    createdTime  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    primary key (id)
);
