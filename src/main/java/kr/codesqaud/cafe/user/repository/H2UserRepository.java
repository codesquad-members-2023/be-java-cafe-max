package kr.codesqaud.cafe.user.repository;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.user.domain.User;
import kr.codesqaud.cafe.user.exception.UserDoesNotMatchException;
import kr.codesqaud.cafe.user.exception.UserIdDuplicateException;
import kr.codesqaud.cafe.user.exception.UserNotExistException;

@Repository
@Primary
public class H2UserRepository implements UserRepository {
	private final JdbcTemplate jdbcTemplate;

	public H2UserRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void save(User user) throws UserIdDuplicateException {
		String sql = "INSERT INTO \"user\"(userId, password, name, email) VALUES (?, ?, ?, ?)";
		try {
			jdbcTemplate.update(sql, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
		} catch (DataAccessException e) {
			throw new UserIdDuplicateException(user.getUserId());
		}
	}

	@Override
	public List<User> findAll() {
		return null;
	}

	@Override
	public User findByUserId(String userId) throws UserNotExistException {
		String sql = "SELECT id, userId, password, name, email FROM \"user\" WHERE userId = ?";
		try {
			return jdbcTemplate.queryForObject(sql, getUserRowMapper(), userId);
		} catch (DataAccessException e) {
			throw new UserNotExistException(userId);
		}
	}

	@Override
	public void modify(User user) throws UserDoesNotMatchException {
		String sql = "UPDATE \"user\" SET name = ?, email = ? WHERE userId = ? AND password = ?";
		try {
			jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getUserId(), user.getPassword());
		} catch (DataAccessException e) {
			throw new UserDoesNotMatchException();
		}
	}

	private RowMapper<User> getUserRowMapper() {
		return (rs, rowNum) ->
			new User(rs.getLong("id"),
				rs.getString("userId"),
				rs.getString("password"),
				rs.getString("name"),
				rs.getString("email"));
	}
}
