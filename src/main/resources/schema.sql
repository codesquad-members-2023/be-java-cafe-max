drop table Article;

create table Article(
    id bigint not null auto_increment,
    writer varchar(30),
    title varchar(200),
    contents varchar(500),
    date datetime,
    primary key (id)
);