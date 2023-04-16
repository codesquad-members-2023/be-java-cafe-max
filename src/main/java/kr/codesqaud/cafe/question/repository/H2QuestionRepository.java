package kr.codesqaud.cafe.question.repository;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.question.dto.request.QuestionWriteRequestDTO;
import kr.codesqaud.cafe.question.dto.response.QuestionDetailResponseDTO;
import kr.codesqaud.cafe.question.dto.response.QuestionTitleResponseDTO;

@Repository
@Primary
public class H2QuestionRepository implements QuestionRepository {
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public H2QuestionRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void insert(QuestionWriteRequestDTO dto) {
		String sql = "INSERT INTO \"post\"(writer, title, contents) VALUES (:writer , :title, :contents)";
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("writer", dto.getWriter())
			.addValue("title", dto.getTitle())
			.addValue("contents", dto.getContents());

		jdbcTemplate.update(sql, parameters);
	}

	public int countAll() {
		String sql = "SELECT COUNT(*) FROM \"post\";";
		return jdbcTemplate.queryForObject(sql, (SqlParameterSource)null, Integer.class);
	}

	public List<QuestionTitleResponseDTO> selectQuestionTitlesByOffset(int postOffset, int pageSize) {
		String sql = "SELECT idx, writer, title, contents, registrationdatetime FROM \"post\" ORDER BY idx ASC LIMIT :pageSize OFFSET :postOffset";
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("postOffset", postOffset)
			.addValue("pageSize", pageSize);
		RowMapper<QuestionTitleResponseDTO> rowMapper = (rs, rowNum) ->
			new QuestionTitleResponseDTO(rs.getInt("idx"), rs.getString("writer"),
				rs.getString("title"),
				rs.getTimestamp("registrationDateTime").toLocalDateTime());

		return jdbcTemplate.query(sql, parameters, rowMapper);
	}

	public QuestionDetailResponseDTO selectById(int id) throws NoSuchElementException {
		String sql = "SELECT id, writer, title, contents, registrationdatetime FROM \"post\"  WHERE idx = :id";
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", id);
		RowMapper<QuestionDetailResponseDTO> rowMapper = (rs, rowNum) ->
			new QuestionDetailResponseDTO(rs.getInt("id"),
				rs.getString("writer"),
				rs.getString("title"),
				rs.getString("contents"),
				rs.getTimestamp("registrationDateTime").toLocalDateTime());

		try {
			return jdbcTemplate.queryForObject(sql, parameters, rowMapper);
		} catch (DataAccessException e) {
			throw new NoSuchElementException("존재하지 않는 개시글 입니다.");
		}

	}

}
