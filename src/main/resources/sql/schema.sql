CREATE TABLE IF NOT EXISTS USER_INFO
(
    userIndex  bigint       AUTO_INCREMENT,
    userID     VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL,
    nickname   VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    signUpDate DATE         NOT NULL,
    PRIMARY KEY (userIndex),
    UNIQUE (nickname)
);

CREATE TABLE IF NOT EXISTS ARTICLE_INFO
(
    articleIndex bigint       AUTO_INCREMENT,
    title     VARCHAR(255) NOT NULL,
    writer    VARCHAR(255) NOT NULL,
    contents  TEXT         NOT NULL,
    writeDate TIMESTAMP(0) NOT NULL,
    hits      bigint       NOT NULL,
    deleted   BOOLEAN      NOT NULL DEFAULT FALSE,
    modDate   TIMESTAMP(0),
    PRIMARY KEY (articleIndex),
    FOREIGN KEY (writer)
    REFERENCES USER_INFO(nickname) ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS COMMENT_INFO
(
    commentIndex bigint       AUTO_INCREMENT,
    articleIndex bigint    NOT NULL,
    author       VARCHAR(255) NOT NULL,
    comment      TEXT         NOT NULL,
    createdDate  TIMESTAMP(0) NOT NULL,
    deleted      BOOLEAN      NOT NULL DEFAULT FALSE,
    modDate      TIMESTAMP(0),
    PRIMARY KEY (commentIndex),
    FOREIGN KEY (author)
    REFERENCES USER_INFO(nickname) ON UPDATE CASCADE
);

DELIMITER $$
CREATE PROCEDURE createNewArticle()
BEGIN DECLARE i INT DEFAULT 1;
WHILE (i <= 80) DO
INSERT INTO ARTICLE_INFO(title, writer, contents, writeDate, hits) VALUES ("제목" + i, "wis", "내용" + i, "2023-04-27 22:23:05", 0);
SET i = i + 1;
END WHILE;
END$$
DELIMITER ;

CALL createNewArticle();

DELIMITER $$
CREATE PROCEDURE createComments()
BEGIN DECLARE i INT DEFAULT 1;
WHILE (i <= 31) DO
INSERT INTO COMMENT_INFO(articleIndex, author, comment, createdDate) VALUES (83, "wisdom", i, "2023-04-27 22:42:24");
SET i = i + 1;
END WHILE;
END$$
DELIMITER ;

CALL createComments();
