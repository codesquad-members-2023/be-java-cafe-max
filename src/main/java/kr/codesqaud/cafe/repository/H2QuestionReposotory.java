package kr.codesqaud.cafe.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

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

}
