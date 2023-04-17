package kr.codesqaud.cafe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.exception.DuplicatedUserIdException;
import kr.codesqaud.cafe.exception.NoSuchUserIdException;

@Repository
public class JdbcUserRepository implements UserRepository {

	private JdbcTemplate jdbcTemplate;

	public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void save(User user) {
		if (isExistsUserId(user.getUserId())) {
			throw new DuplicatedUserIdException();
		}
		jdbcTemplate.update("INSERT INTO user_account(user_id, password, name, email) values (?, ?, ?, ?)",
			user.getUserId(),
			user.getPassword(),
			user.getName(),
			user.getEmail());
	}

	private boolean isExistsUserId(String userId) {
		return jdbcTemplate.queryForObject("SELECT EXISTS(SELECT * FROM user_account WHERE user_id = ?)",
			Boolean.class, userId);
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

	@Override
	public User findUser(String userId) {
		try {
			return jdbcTemplate.queryForObject("SELECT * FROM user_account WHERE user_id = ?", (rs, rowNum) -> new User(
				rs.getString("user_id"),
				rs.getString("password")
			), userId);
		} catch (EmptyResultDataAccessException e) {
			throw new NoSuchUserIdException();
		}
	}

	@Override
	public void modifyUser(User user) {
		if (!isExistsUserId(user.getUserId())) {
			throw new NoSuchUserIdException();
		}
		jdbcTemplate.update("UPDATE user_account SET password = ?, name = ?, email = ? WHERE user_id = ?",
			user.getPassword(),
			user.getName(),
			user.getEmail(),
			user.getUserId());
	}
}
