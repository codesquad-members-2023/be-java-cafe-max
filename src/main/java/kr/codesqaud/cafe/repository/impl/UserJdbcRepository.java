package kr.codesqaud.cafe.repository.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.domain.user.User;
import kr.codesqaud.cafe.repository.UserRepository;

@Repository
public class UserJdbcRepository implements UserRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	private final RowMapper<User> userMapper = (rs, rowNum) -> new User(
		rs.getString("user_id"),
		rs.getString("password"),
		rs.getString("name"),
		rs.getString("email")
	);

	public UserJdbcRepository(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		this.jdbcInsert = new SimpleJdbcInsert(dataSource)
			.withTableName("user_account");
	}

	@Override
	public Optional<User> save(final User user) {
		if (isExistUserByUserId(user.getUserId()).isPresent()) {
			jdbcInsert.execute(new BeanPropertySqlParameterSource(user));
			return Optional.of(user);
		}
		return Optional.empty();
	}

	@Override
	public List<User> findAll() {
		return jdbcTemplate.query("SELECT user_id, password, name, email FROM user_account", userMapper);
	}

	@Override
	public Optional<User> findByUserId(final String userId) {
		try {
			return Optional.ofNullable(
				jdbcTemplate.queryForObject(
					"SELECT user_id, password, name, email FROM user_account WHERE user_id = :userId",
					Map.of("userId", userId), userMapper));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	private Optional<Boolean> isExistUserByUserId(final String userId) {
		try {
			return Optional.of(Boolean.TRUE.equals(jdbcTemplate.queryForObject(
				"SELECT EXISTS (SELECT user_id FROM user_account WHERE user_id = :userId)",
				Map.of("userId", userId), Boolean.class)));
		} catch (final EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public void update(final User user) {
		Map<String, Object> params = Map.of("password", user.getPassword(),
											"name", user.getName(),
											"email", user.getEmail(),
											"userId", user.getUserId());
		jdbcTemplate.update(
			"UPDATE user_account SET password = :password, name = :name, email = :email WHERE user_id = :userId",
			params);
	}
}
