INSERT INTO article(writer, title, contents, created_at, points)
VALUES ('jianId', '안뇽! 첫 게시글 이에요!', '헤헤 게시글 성공!', '20230413', '2');
INSERT INTO article(writer, title, contents, created_at, points)
VALUES ('yukiId', '나능!! 유키당!!', '에오🐱', '20230413', '30');

INSERT INTO reply(writer, contents, created_at, article_id)
VALUES ('jian', '댓글도 성공!', '20230413', '1');
INSERT INTO article(writer, contents, created_at, article_id)
VALUES ('yuki', '🐟냠냠', '20230413', '2');

INSERT INTO `user`(user_id, password, name, email)
VALUES ('jianId', '1234', 'jian', 'jian@gmail.com');
INSERT INTO `user`(user_id, password, name, email)
VALUES ('yukiId', '1234', 'yuki', 'yuki@gmail.com');
