package kr.codesqaud.cafe.user.repository;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.user.dto.request.SignUpDTO;
import kr.codesqaud.cafe.user.dto.response.UserDTO;

@Repository
@Primary
public class H2UserRepository implements UserRepository {
	private final JdbcTemplate jdbcTemplate;

	public H2UserRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void insert(SignUpDTO dto) throws IllegalArgumentException {
		String sql = "INSERT INTO \"user\"(userId, password, name, email) VALUES (?, ?, ?, ?)";
		try {
			jdbcTemplate.update(sql, dto.getUserId(), dto.getPassword(), dto.getName(), dto.getEmail());
		} catch (DataAccessException e) {
			throw new IllegalArgumentException("이미 등록된 아이디 입니다.");
		}
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
