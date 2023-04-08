package kr.codesqaud.cafe.domain.user.repository.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.domain.user.entity.User;
import kr.codesqaud.cafe.domain.user.repository.UserRepository;

@Repository
public class JdbcUserRepositoryImpl implements UserRepository {

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public JdbcUserRepositoryImpl(DataSource dataSource) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public void save(User user) {
		final String SQL = "INSERT INTO users ( password, name, email, dateTime) VALUES (:password, :name, :email,:dateTime)";
		namedParameterJdbcTemplate.update(SQL, new BeanPropertySqlParameterSource(user));
	}

	public List<User> findAll() {
		final String SQL = "SELECT * FROM users";
		return namedParameterJdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(User.class));
	}

	public Optional<User> findById(Long id) {
		final String SQL = "SELECT * FROM users WHERE id = :id";
		User user = namedParameterJdbcTemplate.queryForObject(SQL,
			new MapSqlParameterSource().addValue("id", id),
			BeanPropertyRowMapper.newInstance(User.class));
		if (user == null) {
			return Optional.empty();
		}
		return Optional.of(user);
	}

	public void update(User user) {
		final String SQL = "UPDATE users SET name = :name, email = :email, password = :password WHERE id = :id";
		Map<String, Object> parameter = Map.of(
			"name", user.getName(),
			"email", user.getEmail(),
			"password", user.getPassword(),
			"id", user.getId());
		namedParameterJdbcTemplate.update(SQL, parameter);
	}
}
