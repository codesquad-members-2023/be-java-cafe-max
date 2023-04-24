package kr.codesqaud.cafe.repository.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.domain.comment.Comment;
import kr.codesqaud.cafe.repository.CommentRepository;

@Repository
public class CommentJdbcRepository implements CommentRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	private final RowMapper<Comment> articleCommentRowMapper = (rs, rowNum) -> new Comment(
		rs.getLong("id"),
		rs.getString("content"),
		rs.getTimestamp("created_at").toLocalDateTime(),
		rs.getString("writer"),
		rs.getLong("article_id")
	);

	public CommentJdbcRepository(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		this.jdbcInsert = new SimpleJdbcInsert(dataSource)
			.withTableName("comment")
			.usingColumns("content", "created_at", "writer", "article_id")
			.usingGeneratedKeyColumns("id");
	}

	@Override
	public Long save(final Comment comment) {
		return jdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(comment)).longValue();
	}

	@Override
	public Optional<Comment> findById(final Long id) {
		try {
			return Optional.ofNullable(
				jdbcTemplate.queryForObject(
					"SELECT id, content, created_at, writer, article_id FROM comment WHERE id = :id AND is_deleted = FALSE",
					Map.of("id", id), articleCommentRowMapper));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public List<Comment> findAllByArticleId(final Long articleId) {
		return jdbcTemplate.query(
			"SELECT id, content, created_at, writer, article_id FROM comment WHERE is_deleted = FALSE AND article_id = :articleId",
			Map.of("articleId", articleId),
			articleCommentRowMapper);
	}

	@Override
	public void deleteById(final Long id) {
		jdbcTemplate.update("UPDATE comment SET is_deleted = TRUE WHERE id = :id", Map.of("id", id));
	}
}
