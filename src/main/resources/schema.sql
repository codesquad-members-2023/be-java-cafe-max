-- 이것은 DDL

create table if not exists articles_squad
(
    articleId bigint not null auto_increment,
    writer varchar(16) not null,
    title varchar(32) not null,
    contents varchar(255) not null,
    createdTime datetime not null,
    articleNum bigint not null,
    primary key (id)
);

create table if not exists users_squad     --users_squad 테이블까지 생성완료(변경 가능성 있을지도)
(
    userId bigint not null auto_increment,
    userNum bigint not null,
    userLoginId varchar(16) not null,
    password varchar(32) not null,
--    name varchar(16) not null,
    email varchar(64) not null,
    primary key (userId)
);
