create table article
(
    articleId long        not null auto_increment primary key,
    writer    char(10),
    title     varchar(50),
    contents  varchar(500)
);

create table "user"
(
    userId char(20)       not null primary key,
    password    char(20),
    name     varchar(10),
    email  varchar(30)
);
