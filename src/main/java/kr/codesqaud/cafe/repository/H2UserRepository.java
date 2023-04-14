package kr.codesqaud.cafe.repository;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.dto.SignUpDTO;
import kr.codesqaud.cafe.dto.UserDTO;

@Repository
@Primary
public class H2UserRepository implements UserRepository {
	private final JdbcTemplate jdbcTemplate;

	public H2UserRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void insert(SignUpDTO dto) {
		String sql = "INSERT INTO \"user\"(userId, password, name, email) VALUES (?, ?, ?, ?)";
		jdbcTemplate.update(sql, dto.getUserId(), dto.getPassword(), dto.getName(), dto.getEmail());
	}

	@Override
	public List<UserDTO> selectAll() {
		return null;
	}

	@Override
	public UserDTO selectByUserId(String userId) throws NoSuchElementException {
		return null;
	}

	@Override
	public void update(SignUpDTO dto) throws NoSuchElementException {
		// TODO document why this method is empty
	}
}
