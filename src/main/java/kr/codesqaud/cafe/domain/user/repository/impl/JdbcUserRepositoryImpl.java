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
		final String SQL = "INSERT INTO users ( username, password, nickName, email, dateTime) VALUES (:username,:password, :nickName, :email,:dateTime)";
		namedParameterJdbcTemplate.update(SQL, new BeanPropertySqlParameterSource(user));
	}

	public List<User> findAll() {
		final String SQL = "SELECT * FROM users";
		return namedParameterJdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(User.class));
	}

	public Optional<User> findById(String username) {
		final String SQL = "SELECT * FROM users WHERE username = :username";
		try {
			return namedParameterJdbcTemplate.queryForStream(SQL, Map.of("username", username), userRowMapper)
				.findFirst();
		} catch (DataAccessException e) {
			return Optional.empty();
		}
	}

	@Transactional
	public void update(User user) {
		final String SQL = "UPDATE users SET nickName = :nickName, email = :email WHERE username = :username";
		Map<String, Object> parameter = Map.of(
			"nickName", user.getNickName(),
			"email", user.getEmail(),
			"password", user.getPassword(),
			"username", user.getUsername());
		namedParameterJdbcTemplate.update(SQL, parameter);
	}
}
