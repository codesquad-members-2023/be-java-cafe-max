CREATE TABLE IF NOT EXISTS member (
    id varchar(50) NOT NULL ,
    password varchar(255) NOT NULL,
    name varchar(10) NOT NULL,
    email varchar(50) NOT NULL,
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS article (
    id bigint NOT NULL AUTO_INCREMENT,
    writer varchar(50) NULL,
    title	varchar(50) NOT NULL,
    content	varchar(3000) NOT NULL,
    writetime	datetime NOT NULL,
    PRIMARY KEY (id),
    );
