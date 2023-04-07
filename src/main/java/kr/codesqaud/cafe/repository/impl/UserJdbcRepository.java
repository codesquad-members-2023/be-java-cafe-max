package kr.codesqaud.cafe.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.domain.user.User;
import kr.codesqaud.cafe.repository.UserRepository;

@Repository
public class UserJdbcRepository implements UserRepository {

	private final JdbcTemplate jdbcTemplate;
	private final RowMapper<User> userMapper = (rs, rowNum) -> new User(
		rs.getString("user_id"),
		rs.getString("password"),
		rs.getString("name"),
		rs.getString("email")
	);

	public UserJdbcRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Optional<User> save(User user) {
		if (isExistUserByUserId(user.getUserId())) {
			return Optional.empty();
		}
		jdbcTemplate.update("INSERT INTO user_account VALUES (?, ?, ?, ?)",
			user.getUserId(),
			user.getPassword(),
			user.getName(),
			user.getEmail()
		);
		return Optional.of(user);
	}

	@Override
	public List<User> findAll() {
		return jdbcTemplate.query("SELECT * FROM user_account", userMapper);
	}

	@Override
	public Optional<User> findByUserId(final String userId) {
		User user = jdbcTemplate.queryForObject(
			"SELECT * FROM user_account WHERE user_id = ?",
			userMapper,
			userId
		);
		return Optional.ofNullable(user);
	}

	private boolean isExistUserByUserId(final String userId) {
		Integer existUserCnt = jdbcTemplate.queryForObject(
			"SELECT count(*) FROM user_account WHERE user_id = ?",
			Integer.class,
			userId);
		return existUserCnt != null && existUserCnt > 0;
	}

	@Override
	public void update(final User user) {
		jdbcTemplate.update("UPDATE user_account SET password = ?, name = ?, email = ? WHERE user_id = ?",
			user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
	}
}
