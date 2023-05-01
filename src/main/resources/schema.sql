DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS post;
DROP TABLE IF EXISTS member;

CREATE TABLE member
(
    memberId    BIGINT AUTO_INCREMENT,
    email       VARCHAR(100) NOT NULL UNIQUE,
    password    VARCHAR(100) NOT NULL,
    nickname    VARCHAR(100),
    create_date TIMESTAMP    NOT NULL,
    PRIMARY KEY (memberId)
);

CREATE TABLE post
(
    id           BIGINT       NOT NULL AUTO_INCREMENT,
    title        VARCHAR(255) NOT NULL,
    content      TEXT         NOT NULL,
    writer_email VARCHAR(100) NOT NULL,
    write_date   TIMESTAMP    NOT NULL,
    views        BIGINT DEFAULT 0,
    PRIMARY KEY (id),
    FOREIGN KEY (writer_email) REFERENCES member (email) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS comment
(
    commentId   BIGINT      NOT NULL AUTO_INCREMENT,
    postId      BIGINT      NOT NULL,
    memberId    BIGINT      NOT NULL,
    writer      VARCHAR(20) NOT NULL,
    content     VARCHAR(255),
    create_date TIMESTAMP   NOT NULL,
    update_date TIMESTAMP,

    PRIMARY KEY (commentId),
    FOREIGN KEY (postId) REFERENCES post (id),
    FOREIGN KEY (memberId) REFERENCES member (memberId)
);
