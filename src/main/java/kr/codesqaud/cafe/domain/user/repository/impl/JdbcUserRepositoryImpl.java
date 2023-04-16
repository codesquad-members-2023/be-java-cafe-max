package kr.codesqaud.cafe.domain.user.repository.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import kr.codesqaud.cafe.domain.user.entity.User;
import kr.codesqaud.cafe.domain.user.repository.UserRepository;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepositoryImpl implements UserRepository {

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final RowMapper<User> userRowMapper = BeanPropertyRowMapper.newInstance(User.class);

	public JdbcUserRepositoryImpl(DataSource dataSource) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Transactional
	public void save(User user) {
		final String SQL = "INSERT INTO users ( id, password, name, email, dateTime) VALUES (:id,:password, :name, :email,:dateTime)";
		namedParameterJdbcTemplate.update(SQL, new BeanPropertySqlParameterSource(user));
	}

	public List<User> findAll() {
		final String SQL = "SELECT * FROM users";
		return namedParameterJdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(User.class));
	}

	public Optional<User> findById(String id) {
		final String SQL = "SELECT * FROM users WHERE id = :id";
		try {
			return namedParameterJdbcTemplate.queryForStream(SQL, Map.of("id", id), userRowMapper)
				.findFirst();
		} catch (DataAccessException e) {
			return Optional.empty();
		}
	}

	@Transactional
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
