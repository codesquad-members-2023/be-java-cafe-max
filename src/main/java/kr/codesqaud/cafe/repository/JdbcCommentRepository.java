package kr.codesqaud.cafe.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.domain.Comment;

@Repository
public class JdbcCommentRepository implements CommentRepository {

	private JdbcTemplate jdbcTemplate;

	public JdbcCommentRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void save(Comment comment) {
		jdbcTemplate.update(
			"INSERT INTO article_comment(user_id, contents, created_at, article_id) values (?, ?, ? ,?) ",
			comment.getUserId(),
			comment.getContents(),
			comment.getCreatedAt(),
			comment.getArticleId());
	}

}
