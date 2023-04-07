package kr.codesqaud.cafe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.exception.DuplicatedUserIdException;

@Repository
public class JdbcUserRepository implements UserRepository {

	private JdbcTemplate jdbcTemplate;

	public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void save(User user) {
		findUserProfile(user.getUserId()).ifPresent(u -> {
			throw new DuplicatedUserIdException();
		});
		jdbcTemplate.update("INSERT INTO user_account(user_id, name, email) values (?, ?, ?)",
			user.getUserId(),
			user.getName(),
			user.getEmail());
	}

	@Override
	public List<User> findAll() {
		return jdbcTemplate.query("SELECT * FROM user_account", (rs, rowNum) -> new User(
			rs.getString("user_id"),
			rs.getString("name"),
			rs.getString("email")
		));
	}

	@Override
	public Optional<User> findUserProfile(String id) {
		return Optional.ofNullable(
			jdbcTemplate.queryForObject("SELECT * FROM user_account WHERE user_id = ?", (rs, rowNum) -> new User(
				rs.getString("user_id"),
				rs.getString("name"),
				rs.getString("email")
			), id));
	}
}
