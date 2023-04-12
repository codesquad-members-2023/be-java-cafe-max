drop table if exists articles CASCADE;

CREATE TABLE articles (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         author VARCHAR(255) NOT NULL,
                         title VARCHAR(255) NOT NULL,
                         contents VARCHAR(1000) NOT NULL,
                         time TIMESTAMP NOT NULL
);

drop table if exists users CASCADE;

CREATE TABLE users (
                            userid VARCHAR(255) PRIMARY KEY NOT NULL,
                            password VARCHAR(255) NOT NULL,
                            name VARCHAR(255) NOT NULL,
                            email VARCHAR(255) NOT NULL
);

INSERT INTO users (userid, password, name, email) VALUES ('test', 'Qwer1234', 'testuser', 'test@test.com');

INSERT INTO articles (author, title, contents, time)
VALUES
    ('John', 'My First Article', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.', '2023-04-12 09:30:00'),
    ('Jane', 'My Second Article', 'Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.', '2023-04-12 10:45:00');
