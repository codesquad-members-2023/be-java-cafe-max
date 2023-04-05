package kr.codesqaud.cafe.repository;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import kr.codesqaud.cafe.domain.User;

public class JdbcUserRepository implements UserRepository {
	private final JdbcTemplate jdbcTemplate;

	public JdbcUserRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public User save(User user) {
		return null;
	}

	@Override
	public User update(long index, User user) {
		return null;
	}

	@Override
	public Optional<User> findByUserID(String userID) {
		return Optional.empty();
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return Optional.empty();
	}

	@Override
	public Optional<User> findByNickname(String nickname) {
		return Optional.empty();
	}

	@Override
	public List<User> findAll() {
		return null;
	}
}
