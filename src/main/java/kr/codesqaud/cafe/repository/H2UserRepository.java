package kr.codesqaud.cafe.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.dto.SignUpDTO;

@Repository
public class H2UserRepository {
	private final JdbcTemplate jdbcTemplate;

	public H2UserRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void insert(SignUpDTO dto) {
		String sql = "INSERT INTO \"user\"(userId, password, name, email) VALUES (?, ?, ?, ?)";
		jdbcTemplate.update(sql, dto.getUserId(), dto.getPassword(), dto.getName(), dto.getEmail());
	}
}
