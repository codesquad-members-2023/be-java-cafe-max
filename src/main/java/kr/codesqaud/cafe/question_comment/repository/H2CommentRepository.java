package kr.codesqaud.cafe.question_comment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.question_comment.domain.CommentEntity;

@Repository
public class H2CommentRepository implements CommentRepository {
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public H2CommentRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void save(CommentEntity comment) {
		String sql = "INSERT INTO \"comment\"(post_id, writer_id, content) "
			+ "VALUES (:post_id, :writer_id, :content)";
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("post_id", comment.getPost_id())
			.addValue("writer_id", comment.getWriter_id())
			.addValue("content", comment.getContent());

		jdbcTemplate.update(sql, parameters);
	}

	public List<CommentEntity> findByPostId(long post_id) {
		String sql =
			"SELECT c.id, c.post_id, c.writer_id, u.userId as writer, c.content, c.is_deleted, c.registrationDateTime "
				+ "FROM \"comment\" c "
				+ "JOIN \"user\" u "
				+ "ON c.writer_id = u.id "
				+ "WHERE c.post_id = :post_id "
				+ "AND c.is_deleted = FALSE";
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("post_id", post_id);

		return jdbcTemplate.query(sql, parameters, getReplyRowMapper());
	}

	public Optional<CommentEntity> findById(long id) {
		String sql =
			"SELECT c.id, c.post_id, c.writer_id, u.userId as writer, c.content, c.is_deleted, c.registrationDateTime "
				+ "FROM \"comment\" c "
				+ "JOIN \"user\" u "
				+ "ON c.writer_id = u.id "
				+ "WHERE c.id = :id ";
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", id);

		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(sql, parameters, getReplyRowMapper()));
		} catch (DataAccessException e) {
			return Optional.ofNullable(null);
		}
	}

	public boolean deleteById(long id) {
		String sql = "UPDATE \"comment\" SET is_deleted = TRUE WHERE id = :id";
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", id);

		try {
			jdbcTemplate.update(sql, parameters);
			return true;
		} catch (DataAccessException e) {
			return false;
		}
	}

	private RowMapper<CommentEntity> getReplyRowMapper() {
		return (rs, rowNum) ->
			new CommentEntity(rs.getLong("id"),
				rs.getLong("post_id"),
				rs.getLong("writer_id"),
				rs.getString("writer"),
				rs.getString("content"),
				rs.getBoolean("is_deleted"),
				rs.getTimestamp("registrationDateTime").toLocalDateTime());
	}

}
