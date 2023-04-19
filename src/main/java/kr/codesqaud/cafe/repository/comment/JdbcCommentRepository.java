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
import kr.codesqaud.cafe.exception.CommentNotFoundException;

@Repository
public class JdbcCommentRepository implements CommentRepository {
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public JdbcCommentRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	private RowMapper<Comment> commentRowMapper() {
		return (rs, rowNum) -> new Comment(rs.getLong("index"), rs.getLong("postIndex"), rs.getString("author"),
			rs.getString("comment"), rs.getString("createdDate"), rs.getBoolean("deleted"));
	}

	@Override
	public List<Comment> findByPostIndex(long postIndex) {
		SqlParameterSource param = new MapSqlParameterSource()
			.addValue("postIndex", postIndex);
		return namedParameterJdbcTemplate.query(FIND_COMMENTS_BY_POST_INDEX, param, commentRowMapper());
	}

	@Override
	public void create(Comment comment) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(comment);
		namedParameterJdbcTemplate.update(CREATE_COMMENT, params);
	}

	@Override
	public Comment findOne(Long postIndex, Long index) {
		SqlParameterSource params = new MapSqlParameterSource()
			.addValue("postIndex", postIndex)
			.addValue("index", index);
		List<Comment> comments = namedParameterJdbcTemplate.query(FIND_BY_POST_INDEX_WITH_INDEX, params,
			commentRowMapper());
		return OptionalTo(comments).orElseThrow(CommentNotFoundException::new);
	}

	@Override
	public void delete(Long postIndex, Long index) {
		SqlParameterSource params = new MapSqlParameterSource()
			.addValue("postIndex", postIndex)
			.addValue("index", index);
		namedParameterJdbcTemplate.update(DELETE_COMMENT, params);
	}

	private Optional<Comment> OptionalTo(List<Comment> comments) {
		return comments.stream().findAny();
	}
}
