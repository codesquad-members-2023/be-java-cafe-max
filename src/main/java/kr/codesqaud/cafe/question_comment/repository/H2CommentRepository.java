package kr.codesqaud.cafe.question_comment.repository;

import java.util.List;

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
				+ "WHERE c.post_id = :post_id ";
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("post_id", post_id);

		return jdbcTemplate.query(sql, parameters, getReplyMapper());
	}

	private RowMapper<CommentEntity> getReplyMapper() {
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
