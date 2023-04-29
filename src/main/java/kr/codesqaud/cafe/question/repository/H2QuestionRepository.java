package kr.codesqaud.cafe.question.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.question.domain.QuestionEntity;

@Repository
@Primary
public class H2QuestionRepository implements QuestionRepository {
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public H2QuestionRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void save(QuestionEntity question) {
		String sql = "INSERT INTO \"post\"(writer_id, title, contents) VALUES (:writer_id , :title, :contents)";
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("writer_id", question.getWriter_id())
			.addValue("title", question.getTitle())
			.addValue("contents", question.getContents());

		jdbcTemplate.update(sql, parameters);
	}

	public long countBy() {
		String sql = "SELECT COUNT(*) FROM \"post\" WHERE is_deleted = FALSE;";
		return jdbcTemplate.queryForObject(sql, (SqlParameterSource)null, Long.class);
	}

	public List<QuestionEntity> findPageBy(long offset, int pageSize) {
		String sql =
			"SELECT p.id, p.writer_id, u.userId as writer, p.title, p.contents, p.is_deleted, p.registrationdatetime "
				+ "FROM \"post\" p "
				+ "JOIN \"user\" u "
				+ "ON p.writer_id = u.id "
				+ "WHERE is_deleted = FALSE "
				+ "ORDER BY id DESC "
				+ "LIMIT :pageSize OFFSET :offset";

		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("offset", offset)
			.addValue("pageSize", pageSize);

		return jdbcTemplate.query(sql, parameters, getQuestionRowMapper());
	}

	public Optional<QuestionEntity> findById(long id) {
		String sql =
			"SELECT p.id, p.writer_id, u.userId as writer, p.title, p.contents, p.is_deleted, p.registrationdatetime "
				+ "FROM \"post\" p "
				+ "JOIN \"user\" u "
				+ "ON p.writer_id = u.id "
				+ "WHERE p.id = :id "
				+ "AND is_deleted = FALSE";

		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", id);

		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(sql, parameters, getQuestionRowMapper()));
		} catch (DataAccessException e) {
			return Optional.ofNullable(null);
		}

	}

	@Override
	public boolean update(QuestionEntity question) {
		String sql = "UPDATE \"post\" SET title = :title, contents = :contents WHERE id = :id";

		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("title", question.getTitle())
			.addValue("contents", question.getContents())
			.addValue("id", question.getId());

		try {
			jdbcTemplate.update(sql, parameters);
			return true;
		} catch (DataAccessException e) {
			return false;
		}
	}

	public boolean delete(long id) {
		String sql = "UPDATE \"post\" SET is_deleted = TRUE WHERE id = :id";

		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", id);

		try {
			jdbcTemplate.update(sql, parameters);
			return true;
		} catch (DataAccessException e) {
			return false;
		}
	}

	private RowMapper<QuestionEntity> getQuestionRowMapper() {
		return (rs, rowNum) ->
			new QuestionEntity(rs.getLong("id"),
				rs.getLong("writer_id"),
				rs.getString("writer"),
				rs.getString("title"),
				rs.getString("contents"),
				rs.getBoolean("is_deleted"),
				rs.getTimestamp("registrationDateTime").toLocalDateTime());
	}

}
