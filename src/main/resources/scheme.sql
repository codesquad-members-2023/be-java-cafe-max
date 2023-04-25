CREATE TABLE IF NOT EXISTS user
(
    id       bigint       NOT NULL AUTO_INCREMENT,
    login_id varchar(10)  NOT NULL,
    password varchar(10)  NOT NULL,
    name     varchar(10)  NOT NULL,
    email    varchar(320) NOT NULL,
    primary key (id, login_id)
);

ALTER TABLE user
    ADD INDEX (login_id);

CREATE TABLE IF NOT EXISTS article
(
    id            bigint      NOT NULL AUTO_INCREMENT,
    user_login_id varchar(10) NOT NULL,
    title         varchar(10) NOT NULL,
    contents      text(1000)  NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_login_id)
        REFERENCES user (login_id) ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS reply
(
    id              bigint      NOT NULL AUTO_INCREMENT,
    article_id      bigint      NOT NULL,
    user_login_id   varchar(10) NOT NULL,
    contents        text(1000)  NOT NULL,
    create_dateTime DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (article_id)
        REFERENCES article (id) ON UPDATE CASCADE,
    FOREIGN KEY (user_login_id)
        REFERENCES user (login_id) ON UPDATE CASCADE
);
