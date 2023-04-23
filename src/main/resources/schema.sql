
CREATE TABLE IF NOT EXISTS Users (
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE ,
    password  VARCHAR(50) NOT NULL,
    nickName      VARCHAR(50) NOT NULL UNIQUE ,
    email     VARCHAR(50) NOT NULL,
    dateTime TIMESTAMP NOT NULL
    );

CREATE TABLE IF NOT EXISTS Article (
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    writer    VARCHAR (50) NOT NULL,
    title     VARCHAR(50) NOT NULL,
    content  TEXT NOT NULL,
    dateTime TIMESTAMP NOT NULL
    );


CREATE TABLE IF NOT EXISTS Reply (
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    articleId BIGINT NOT NULL,
    writer    VARCHAR (50) NOT NULL,
    content  TEXT NOT NULL,
    dateTime TIMESTAMP NOT NULL,
    INDEX(articleId)
    );

