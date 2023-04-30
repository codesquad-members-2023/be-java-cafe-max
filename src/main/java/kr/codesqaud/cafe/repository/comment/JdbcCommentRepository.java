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
	public static final int COMMENT_SIZE = 15;
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public JdbcCommentRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	private RowMapper<Comment> commentRowMapper() {
		return (rs, rowNum) -> new Comment(rs.getLong("commentIndex"), rs.getLong("articleIndex"),
			rs.getString("author"), rs.getString("comment"), rs.getString("createdDate"), rs.getBoolean("deleted"));
	}

	@Override
	public void create(Comment comment) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(comment);
		namedParameterJdbcTemplate.update(CREATE_COMMENT, params);
	}

	@Override
	public Optional<Comment> findOne(Long commentIndex) {
		SqlParameterSource params = new MapSqlParameterSource()
			.addValue("commentIndex", commentIndex);
		List<Comment> comments = namedParameterJdbcTemplate.query(FIND_BY_COMMENT_INDEX, params, commentRowMapper());
		return optionalTo(comments);
	}

	@Override
	public void delete(Long commentIndex) {
		SqlParameterSource params = new MapSqlParameterSource()
			.addValue("commentIndex", commentIndex);
		namedParameterJdbcTemplate.update(DELETE_COMMENT, params);
	}

	@Override
	public void deleteAll(Long articleIndex) {
		SqlParameterSource param = new MapSqlParameterSource()
			.addValue("articleIndex", articleIndex);
		namedParameterJdbcTemplate.update(DELETE_ALL_COMMENT, param);
	}

	@Override
	public Integer getCommentsSize(Long articleIndex) {
		SqlParameterSource param = new MapSqlParameterSource()
			.addValue("articleIndex", articleIndex);
		return namedParameterJdbcTemplate.queryForObject(COUNT_COMMENTS_SIZE, param, Integer.class);
	}

	@Override
	public Boolean equalsAuthor(Long articleIndex) {
		SqlParameterSource param = new MapSqlParameterSource()
			.addValue("articleIndex", articleIndex);
		return namedParameterJdbcTemplate.queryForObject(EQUAL_AUTHOR, param, Boolean.class);
	}

	@Override
	public List<Comment> findComments(Long articleIndex, Long commentLastIndex) {
		SqlParameterSource params = new MapSqlParameterSource()
			.addValue("articleIndex", articleIndex)
			.addValue("commentLastIndex", commentLastIndex)
			.addValue("commentSize", COMMENT_SIZE);
		return namedParameterJdbcTemplate.query(FIND_MORE_COMMENTS, params, commentRowMapper());
	}

	private Optional<Comment> optionalTo(List<Comment> comments) {
		return comments.stream().findAny();
	}

	@Override
	public void update(Long commentIndex, Comment comment) {
		SqlParameterSource params = new MapSqlParameterSource()
			.addValue("commentIndex", commentIndex)
			.addValue("comment", comment.getComment())
			.addValue("modDate", comment.getModDate());
		namedParameterJdbcTemplate.update(UPDATE_COMMENT, params);
	}
}
