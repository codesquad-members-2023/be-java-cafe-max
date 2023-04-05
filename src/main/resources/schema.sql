CREATE TABLE IF NOT EXISTS article
(
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    writer     VARCHAR(64)  NOT NULL,
    title      VARCHAR(64)  NOT NULL,
    content    VARCHAR(255) NOT NULL,
    created_at DATETIME     NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS user_account
(
    user_id  VARCHAR(64)  NOT NULL,
    password VARCHAR(255) NOT NULL,
    name     VARCHAR(64)  NOT NULL,
    email    VARCHAR(64)  NOT NULL,
    PRIMARY KEY (user_id)
);