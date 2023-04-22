package kr.codesqaud.cafe.question.repository;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.question.domain.Question;
import kr.codesqaud.cafe.question.exception.QuestionNotExistException;

@Repository
@Primary
public class H2QuestionRepository implements QuestionRepository {
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public H2QuestionRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void save(Question question) {
		String sql = "INSERT INTO \"post\"(writer, title, contents) VALUES (:writer , :title, :contents)";
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("writer", question.getWriter())
			.addValue("title", question.getTitle())
			.addValue("contents", question.getContents());

		jdbcTemplate.update(sql, parameters);
	}

	public long countBy() {
		String sql = "SELECT COUNT(*) FROM \"post\";";
		return jdbcTemplate.queryForObject(sql, (SqlParameterSource)null, Integer.class);
	}

	public List<Question> findAll(long offset, int pageSize) {
		String sql = "SELECT id, writer, title, contents, registrationdatetime FROM \"post\" ORDER BY id DESC LIMIT :pageSize OFFSET :postOffset";
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("postOffset", offset)
			.addValue("pageSize", pageSize);

		return jdbcTemplate.query(sql, parameters, getQuestionRowMapper());
	}

	public Question findById(long id) throws QuestionNotExistException {
		String sql = "SELECT id, writer, title, contents, registrationdatetime FROM \"post\"  WHERE id = :id";
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", id);

		try {
			return jdbcTemplate.queryForObject(sql, parameters, getQuestionRowMapper());
		} catch (DataAccessException e) {
			throw new QuestionNotExistException(id);
		}

	}

	private RowMapper<Question> getQuestionRowMapper() {
		return (rs, rowNum) ->
			new Question(rs.getInt("id"),
				rs.getString("writer"),
				rs.getString("title"),
				rs.getString("contents"),
				rs.getTimestamp("registrationDateTime").toLocalDateTime());
	}

}
