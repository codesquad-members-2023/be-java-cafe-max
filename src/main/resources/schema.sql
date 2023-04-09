DROP TABLE IF EXISTS USERS CASCADE;
CREATE TABLE USERS
(
    id       BIGINT AUTO_INCREMENT,
    userId   VARCHAR(255)   NOT NULL,
    password VARBINARY(255) NOT NULL,
    name     VARCHAR(255)   NOT NULL,
    email    VARCHAR(255)   NOT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS ARTICLES CASCADE;
CREATE TABLE ARTICLES
(
    id          bigint auto_increment,
    writer      VARCHAR(255) NOT NULL,
    title       VARCHAR(255) NOT NULL,
    contents    TEXT         NOT NULL,
    currentTime TIMESTAMP    NOT NULL,
    PRIMARY KEY (id)
);