INSERT INTO article(writer, title, contents, created_at, points, deleted)
VALUES ('jianId', '안뇽! 첫 게시글 이에요!', '헤헤 게시글 성공!', '20230413', '2', '0');
INSERT INTO article(writer, title, contents, created_at, points, deleted)
VALUES ('yukiId', '나능!! 유키당!!', '에오🐱', '20230413', '30', '0');

INSERT INTO reply(writer, contents, created_at, article_id, deleted)
VALUES ('jianId', '댓글도 성공!', '20230413', '1', '0');
INSERT INTO reply(writer, contents, created_at, article_id, deleted)
VALUES ('yukiId', '🐟냠냠', '20230413', '2', '0');

INSERT INTO `user`(user_id, password, name, email, deleted)
VALUES ('jianId', '1234', 'jian', 'jian@gmail.com', '0');
INSERT INTO `user`(user_id, password, name, email, deleted)
VALUES ('yukiId', '1234', 'yuki', 'yuki@gmail.com', '0');
