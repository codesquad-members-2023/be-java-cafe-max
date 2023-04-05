CREATE TABLE IF NOT EXISTS post(
    postid BIGINT NOT NULL AUTO_INCREMENT,
    writer VARCHAR(50) NOT NULL,
    title VARCHAR(255) NOT NULL,
    contents VARCHAR(255) NOT NULL,
    writedatetime TIMESTAMP NOT NULL,
    PRIMARY KEY (postid)
);

CREATE TABLE IF NOT EXISTS users(
    userid VARCHAR NOT NULL,
    password VARCHAR NOT NULL,
    username VARCHAR NOT NULL,
    email VARCHAR NOT NULL,
    PRIMARY KEY (userid)
);
