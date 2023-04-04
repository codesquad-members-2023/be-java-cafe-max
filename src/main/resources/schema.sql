
CREATE TABLE IF NOT EXISTS "user"
(
    userId varchar(50),
    password varchar(50) not null,
    "name" varchar(50) not null,
    email varchar(50) not null,
    primary key (userId)
);

