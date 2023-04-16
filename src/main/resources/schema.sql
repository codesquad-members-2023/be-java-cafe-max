CREATE TABLE IF NOT EXISTS Users (
    id        VARCHAR(50) PRIMARY KEY,
    password  VARCHAR(50) NOT NULL,
    name      VARCHAR(50) NOT NULL,
    email     VARCHAR(50) NOT NULL,
    dateTime TIMESTAMP NOT NULL
    );

CREATE TABLE IF NOT EXISTS Article (
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    title     VARCHAR(50) NOT NULL,
    content  TEXT NOT NULL,
    dateTime TIMESTAMP NOT NULL
    );

