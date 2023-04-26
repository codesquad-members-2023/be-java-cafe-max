package kr.codesqaud.cafe.repository.comment;

import static kr.codesqaud.cafe.repository.comment.CommentSql.*;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.domain.Comment;

@Repository
public class JdbcCommentRepository implements CommentRepository {
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public JdbcCommentRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	private RowMapper<Comment> commentRowMapper() {
		return (rs, rowNum) -> new Comment(rs.getLong("commentIndex"), rs.getLong("articleIndex"),
			rs.getString("author"),
			rs.getString("comment"), rs.getString("createdDate"), rs.getBoolean("deleted"));
	}

	@Override
	public List<Comment> findByArticleIndex(Long articleIndex) {
		SqlParameterSource param = new MapSqlParameterSource()
			.addValue("articleIndex", articleIndex);
		return namedParameterJdbcTemplate.query(FIND_COMMENTS_BY_ARTICLE_INDEX, param, commentRowMapper());
	}

	@Override
	public void create(Comment comment) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(comment);
		namedParameterJdbcTemplate.update(CREATE_COMMENT, params);
	}

	@Override
	public Optional<Comment> findOne(Long articleIndex, Long commentIndex) {
		SqlParameterSource params = new MapSqlParameterSource()
			.addValue("articleIndex", articleIndex)
			.addValue("commentIndex", commentIndex);
		List<Comment> comments = namedParameterJdbcTemplate.query(FIND_BY_ARTICLE_INDEX_WITH_COMMENT_INDEX, params,
			commentRowMapper());
		return optionalTo(comments);
	}

	@Override
	public void delete(Long articleIndex, Long commentIndex) {
		SqlParameterSource params = new MapSqlParameterSource()
			.addValue("articleIndex", articleIndex)
			.addValue("commentIndex", commentIndex);
		namedParameterJdbcTemplate.update(DELETE_COMMENT, params);
	}

	@Override
	public void deleteAll(Long articleIndex) {
		SqlParameterSource param = new MapSqlParameterSource()
			.addValue("articleIndex", articleIndex);
		namedParameterJdbcTemplate.update(DELETE_ALL_COMMENT, param);
	}

	private Optional<Comment> optionalTo(List<Comment> comments) {
		return comments.stream().findAny();
	}
}
