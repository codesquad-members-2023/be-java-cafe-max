package kr.codesqaud.cafe.user.repository;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.user.domain.User;
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
		return null;
	}

	@Override
	public void modify(User user) throws UserNotExistException {
		// TODO document why this method is empty
	}
}
