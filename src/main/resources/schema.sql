-- CREATE SCHEMA IF NOT EXISTS db;

CREATE TABLE IF NOT EXISTS users
(
    user_id    VARCHAR(64)  NOT NULL,
    password   VARCHAR(255) NOT NULL,
    user_name  VARCHAR(64)  NOT NULL,
    user_email VARCHAR(64)  NOT NULL,
    PRIMARY KEY (user_id)
    );

CREATE TABLE IF NOT EXISTS articles
(
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    writer     VARCHAR(64)  NOT NULL,
    title      VARCHAR(64)  NOT NULL,
    content    VARCHAR(255) NOT NULL,
    created_at DATETIME     NOT NULL,
    PRIMARY KEY (id)
    );
