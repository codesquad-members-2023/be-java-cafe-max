create table article
(
    id         bigint   not null auto_increment,
    writer     varchar  not null,
    title      varchar  not null,
    contents   varchar  not null,
    created_at datetime not null,
    primary key (id)
);
