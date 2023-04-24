insert into users(userId, password, name, email) values ('Joy', '1234', 'Joy', '123@123')
insert into users(userId, password, name, email) values ('abc', '1234', 'spring', '456@456')
insert into users(userId, password, name, email) values ('kim', '1234', 'kim', '789@789')

insert into article(writer, userId, title, contents ) values ('Joy', 'Joy', 'welcome','환영합니당')
insert into article(writer, userId, title, contents ) values ('spring', 'abc','봄이다','스프링 어려웡')
insert into article(writer, userId, title, contents ) values ('kim', 'kim','ajax','가 뭐지?')

insert into reply(writer, userId, contents, articleId) values ('Joy', 'Joy', '댓글', '2')