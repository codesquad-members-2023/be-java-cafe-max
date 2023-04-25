DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE users
(
    id       bigint auto_increment,
    userId   VARCHAR(255) NOT NULL,
    name     VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL,
    primary key (id)
);

DROP TABLE IF EXISTS question CASCADE;
CREATE TABLE question
(
    id         bigint auto_increment,
    title      VARCHAR(255) NOT NULL,
    content    TEXT         NOT NULL,
    createTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modifyTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    userId     bigint       NOT NULL,
    primary key (id),
    foreign key (userId) references users (id) ON DELETE CASCADE
);
