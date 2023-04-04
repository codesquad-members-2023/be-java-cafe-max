DROP TABLE IF EXISTS post;

DROP TABLE IF EXISTS member;

CREATE TABLE member (
    id bigint NOT NULL AUTO_INCREMENT,
    email varchar(50) NOT NULL,
    password varchar(255) NOT NULL,
    nickname varchar(10) NOT NULL,
    create_date	datetime NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE post (
    id bigint NOT NULL AUTO_INCREMENT,
    title	varchar(50) NOT NULL,
    content	varchar(3000) NOT NULL,
    writer_id bigint NOT NULL,
    write_date	datetime NOT NULL,
    views bigint NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (writer_id) references member(id)
);
