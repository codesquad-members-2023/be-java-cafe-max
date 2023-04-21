INSERT INTO member(email, password, nickname, create_date)
VALUES('test@gmail.com', 'Test1234', 'test', NOW());

INSERT INTO member(email, password, nickname, create_date)
VALUES('mandu@gmail.com', 'Mandu1234', '만두', NOW());

INSERT INTO post(title, content, writer_id, write_date, views, is_deleted)
VALUES('테스트', '게시글 댓글!!', 1, NOW(), 0, false);

INSERT INTO comment(post_id, writer_id, content, write_date, is_deleted)
VALUES(1, 1, '테스트 댓글', NOW(), false);

INSERT INTO comment(post_id, writer_id, content, write_date, is_deleted)
VALUES(1, 2, '만두 댓글', NOW(), false);
