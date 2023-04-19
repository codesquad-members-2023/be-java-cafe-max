package kr.codesqaud.cafe.repository;

import java.util.List;

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

	@Override
	public List<Comment> articleComment(Long id) {
		return jdbcTemplate.query("SELECT * FROM article_comment WHERE article_id = ?", (rs, rowNum) -> new Comment(
			rs.getLong("id"),
			rs.getString("user_id"),
			rs.getString("contents"),
			rs.getTimestamp("created_at").toLocalDateTime(),
			rs.getLong("article_id")
		), id);
	}

	@Override
	public void delete(Long id) {
		jdbcTemplate.update("DELETE FROM ARTICLE_COMMENT WHERE id = ?", id);
	}
}
