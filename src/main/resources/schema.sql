CREATE TABLE IF NOT EXISTS article
(
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    writer     VARCHAR(64)  NOT NULL,
    title      VARCHAR(64)  NOT NULL,
    content    VARCHAR(255) NOT NULL,
    created_at DATETIME     NOT NULL,
    is_deleted BOOLEAN      NOT NULL DEFAULT FALSE,
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

CREATE TABLE IF NOT EXISTS article_comment
(
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    content    VARCHAR(255) NOT NULL,
    created_at DATETIME     NOT NULL,
    is_deleted BOOLEAN      NOT NULL DEFAULT FALSE,
    writer     VARCHAR(64)  NOT NULL,
    article_id BIGINT       NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE article_comment
    DROP INDEX article_id_idx;
CREATE INDEX article_id_idx ON article_comment (article_id);
