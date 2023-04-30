drop table if exists article CASCADE;

CREATE TABLE article (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         author VARCHAR(255) NOT NULL,
                         title VARCHAR(255) NOT NULL,
                         contents VARCHAR(1000) NOT NULL,
                         created_time DATETIME NOT NULL
);

drop table if exists user_account CASCADE;

CREATE TABLE user_account (
                            userid VARCHAR(255) PRIMARY KEY NOT NULL,
                            password VARCHAR(255) NOT NULL,
                            name VARCHAR(255) NOT NULL,
                            email VARCHAR(255) NOT NULL
);

drop table if exists comment CASCADE;

CREATE TABLE comment (
                          comment_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          article_id VARCHAR(255) NOT NULL,
                          contents VARCHAR(1000) NOT NULL,
                          user_id VARCHAR(255) NOT NULL,
                          created_time DATETIME NOT NULL
);


INSERT INTO user_account (userid, password, name, email) VALUES ('test', 'Qwer1234', 'testuser', 'test@test.com');
