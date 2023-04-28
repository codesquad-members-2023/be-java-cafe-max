SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS question CASCADE;
DROP TABLE IF EXISTS comment CASCADE;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE users
(
    id       bigint auto_increment,
    userId   VARCHAR(255) NOT NULL,
    name     VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL,
    primary key (id)
);


CREATE TABLE question
(
    id         bigint auto_increment,
    title      VARCHAR(255) NOT NULL,
    content    TEXT         NOT NULL,
    createTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modifyTime TIMESTAMP DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    deleted    boolean   DEFAULT false,
    userId     bigint       NOT NULL,
    primary key (id),
    foreign key (userId) references users (id) ON DELETE CASCADE
);


CREATE TABLE comment
(
    id         bigint auto_increment,
    content    VARCHAR(3000) NOT NULL,
    createTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modifyTime TIMESTAMP DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    deleted    boolean   DEFAULT false,
    userId     bigint        NOT NULL,
    questionId bigint        NOT NULL,
    primary key (id),
    foreign key (userId) references users (id) ON DELETE CASCADE,
    foreign key (questionId) references question (id) ON DELETE CASCADE
);
