CREATE TABLE IF NOT EXISTS USERS
(
    id       BIGINT AUTO_INCREMENT,
    userId   VARCHAR(13)   NOT NULL,
    password VARBINARY(32) NOT NULL,
    name     VARCHAR(10)   NOT NULL,
    email    VARCHAR(30)   NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS ARTICLES
(
    id          bigint auto_increment,
    userId      VARCHAR(13)  NOT NULL,
    title       VARCHAR(30) NOT NULL,
    contents    TEXT         NOT NULL,
    currentTime TIMESTAMP    NOT NULL,
    PRIMARY KEY (id)
);