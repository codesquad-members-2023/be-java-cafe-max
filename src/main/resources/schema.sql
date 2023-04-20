CREATE TABLE IF NOT EXISTS `USER`
(
    user_id  VARCHAR(12) PRIMARY KEY NOT NULL,
    nickName VARCHAR(12)             NOT NULL,
    email    VARCHAR(255)            NOT NULL,
    password VARCHAR(255)            NOT NULL,
    date     VARCHAR(10)             NOT NULL
);

CREATE TABLE IF NOT EXISTS `ARTICLE`
(
    article_idx BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id     VARCHAR(12)   NOT NULL,
    nickName    VARCHAR(12)   NOT NULL,
    content     VARCHAR(3000) NOT NULL,
    title       VARCHAR(255)  NOT NULL,
    date        VARCHAR(10)   NOT NULL,
    is_visible  BOOL DEFAULT 1
);

CREATE TABLE IF NOT EXISTS `REPLY`
(
    reply_idx   BIGINT PRIMARY KEY AUTO_INCREMENT,
    article_idx BIGINT       NOT NULL,
    user_id     VARCHAR(12)  NOT NULL,
    nickName    VARCHAR(12)  NOT NULL,
    content     VARCHAR(255) NOT NULL,
    date        VARCHAR(10)  NOT NULL,
    is_visible  BOOL DEFAULT 1,
    FOREIGN KEY (article_idx) REFERENCES `ARTICLE` (article_idx)
);
