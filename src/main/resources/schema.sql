DROP TABLE IF EXISTS USERS CASCADE;
CREATE TABLE USERS
(
    id       BIGINT AUTO_INCREMENT,
    userId   VARCHAR(50)   NOT NULL,
    password VARBINARY(50) NOT NULL,
    name     VARCHAR(50)   NOT NULL,
    email    VARCHAR(50)   NOT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS ARTICLES CASCADE;
CREATE TABLE ARTICLES
(
    id          bigint auto_increment,
    writer      VARCHAR(50) NOT NULL,
    title       VARCHAR(100) NOT NULL,
    contents    TEXT         NOT NULL,
    currentTime TIMESTAMP    NOT NULL,
    PRIMARY KEY (id)
);