package kr.codesqaud.cafe.account.repository;

import static kr.codesqaud.cafe.account.exception.ErrorCode.*;
import static kr.codesqaud.cafe.utils.FiledName.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.account.exception.repository.GetAllUsersFailedException;
import kr.codesqaud.cafe.account.exception.repository.NoSuchEmailException;
import kr.codesqaud.cafe.account.exception.repository.NoSuchIdException;
import kr.codesqaud.cafe.account.exception.repository.SaveUserFailedException;
import kr.codesqaud.cafe.account.exception.repository.UpdateUserFailedException;
import kr.codesqaud.cafe.account.service.User;

@Repository
public class UserRepository {

	public static final String COLUMN_USER_ID = "user_id";
	public static final String COLUMN_PASSWORD = "password";
	public static final String COLUMN_NICKNAME = "nickname";
	public static final String COLUMN_EMAIL = "email";
	private static final String QUERY_UPDATE = "UPDATE USERS SET NICKNAME = ?, EMAIL = ? WHERE USER_ID = ?";
	private static final String QUERY_FIND_BY_ID = "SELECT EMAIL,NICKNAME,PASSWORD FROM USERS WHERE USER_ID = ?";
	private static final String QUERY_FIND_BY_EMAIL = "SELECT USER_ID,NICKNAME,PASSWORD FROM USERS WHERE EMAIL = ?";
	private static final String QUERY_CONTAINS_EMAIL = "SELECT COUNT(*) FROM USERS WHERE EMAIL = ?";
	private static final String QUERY_FIND_ALL_USERS = "SELECT * FROM USERS";
	private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);
	private final JdbcTemplate jdbcTemplate;
	private final DataSource dataSource;

	public UserRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.dataSource = dataSource;
	}

	public int save(User user) {
		try {
			SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(USERS)
				.usingGeneratedKeyColumns(COLUMN_USER_ID);
			Map<String, Object> parameters = new HashMap<>();
			parameters.put(COLUMN_EMAIL, user.getEmail());
			parameters.put(COLUMN_NICKNAME, user.getNickname());
			parameters.put(COLUMN_PASSWORD, user.getPassword());
			return (int)simpleJdbcInsert.executeAndReturnKey(parameters);
		} catch (DataAccessException e) {
			logger.error("[ Message = {} ][ Nickname = {} ][ Email = {} ][ Password = {} ]",
				SAVE_USER_FAILED_CODE.getMessage(),
				user.getNickname(),
				user.getEmail(),
				user.getPassword());
			throw new SaveUserFailedException(SAVE_USER_FAILED_CODE);
		}
	}

	public List<User> getAllMembers() {
		try {
			return jdbcTemplate.query(QUERY_FIND_ALL_USERS, (resultSet, rowNum)
				-> new User.Builder()
				.id(resultSet.getLong(COLUMN_USER_ID))
				.password(resultSet.getString(COLUMN_PASSWORD).trim())
				.nickname(resultSet.getString(COLUMN_NICKNAME).trim())
				.email(resultSet.getString(COLUMN_EMAIL).trim())
				.build()
			);
		} catch (DataAccessException e) {
			logger.error("Message = {}", GET_ALL_USERS_FAILED_CODE.getMessage());
			throw new GetAllUsersFailedException(GET_ALL_USERS_FAILED_CODE);
		}
	}

	public Optional<User> findById(Long userId) {
		try {
			RowMapper<User> userRowMapper = (resultSet, rowNum) -> new User.Builder()
				.id(userId)
				.email(resultSet.getString(COLUMN_EMAIL).trim())
				.nickname(resultSet.getString(COLUMN_NICKNAME).trim())
				.password(resultSet.getString(COLUMN_PASSWORD).trim())
				.build();
			return Optional.ofNullable(this.jdbcTemplate.queryForObject(QUERY_FIND_BY_ID, userRowMapper, userId));
		} catch (DataAccessException e) {
			logger.error("[ Message = {} ][ UserId = {} ]", NO_SUCH_ID_CODE.getMessage(), userId);
			return Optional.empty();
		}
	}

	public Optional<User> findByEmail(String email) {
		try {
			RowMapper<User> userRowMapper = (resultSet, rowNum) -> new User.Builder()
				.id(resultSet.getLong(COLUMN_USER_ID))
				.nickname(resultSet.getString(COLUMN_NICKNAME).trim())
				.password(resultSet.getString(COLUMN_PASSWORD).trim())
				.build();
			return Optional.ofNullable(this.jdbcTemplate.queryForObject(QUERY_FIND_BY_EMAIL, userRowMapper, email));
		} catch (DataAccessException e) {
			logger.error("[ Message = {} ][ Email = {} ]", NO_SUCH_EMAIL_CODE.getMessage(), email);
			throw new NoSuchEmailException(NO_SUCH_EMAIL_CODE);
		}
	}

	public boolean containEmail(String email) {
		Integer count = jdbcTemplate.queryForObject(QUERY_CONTAINS_EMAIL, Integer.class, email);
		return count != null && count != 0;
	}

	public void update(User user) {
		try {
			jdbcTemplate.update(QUERY_UPDATE, user.getNickname(), user.getEmail(),
				user.getId());
		} catch (DataAccessException e) {
			logger.error("[ Message = {} ][ Id = {} ][ Nickname = {} ][ Email = {} ]",
				UPDATE_USER_FAILED_CODE.getMessage(), user.getId(), user.getNickname(), user.getEmail());
			throw new UpdateUserFailedException(UPDATE_USER_FAILED_CODE);
		}
	}
}
