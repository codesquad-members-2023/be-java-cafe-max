package kr.codesqaud.cafe.repository;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.dto.QuestionTitleDTO;
import kr.codesqaud.cafe.dto.QuestionWriteDTO;

@Repository
@Primary
public class H2QuestionReposotory {
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public H2QuestionReposotory(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void insert(QuestionWriteDTO dto) {
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

	public List<QuestionTitleDTO> selectQuestionTitlesByOffset(int postOffset, int pageSize) {
		String sql = "SELECT idx, writer, title, contents, registrationdatetime FROM \"post\" ORDER BY idx ASC LIMIT :pageSize OFFSET :postOffset";
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("postOffset", postOffset)
			.addValue("pageSize", pageSize);
		RowMapper<QuestionTitleDTO> rowMapper = (rs, rowNum) ->
			new QuestionTitleDTO(rs.getInt("idx"), rs.getString("writer"),
				rs.getString("title"),
				rs.getTimestamp("registrationDateTime").toLocalDateTime());
		
		return jdbcTemplate.query(sql, parameters, rowMapper);
	}

}
