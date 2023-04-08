package kr.codesqaud.cafe.account;

import kr.codesqaud.cafe.account.exception.GetAllUsersFailedException;
import kr.codesqaud.cafe.account.exception.SaveUserFailedException;
import kr.codesqaud.cafe.account.exception.UpdateUserFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static kr.codesqaud.cafe.exception.ErrorCode.*;

@Repository
public class UserRepository {

    private static final String COLUMN_USER_ID = "USER_ID";
    private static final String COLUMN_PASSWORD = "PASSWORD";
    private static final String COLUMN_NICKNAME = "NICKNAME";
    private static final String COLUMN_EMAIL = "EMAIL";
    private static final String QUERY_UPDATE = "UPDATE USERS SET NICKNAME = ?, EMAIL = ? WHERE USER_ID = ?";
    private static final String QUERY_SAVE = "INSERT INTO USERS (NICKNAME, EMAIL, PASSWORD) values ( :nickname,:email,:password )";
    private static final String QUERY_FIND_BY_ID = "SELECT USER_ID,EMAIL,NICKNAME,PASSWORD FROM USERS WHERE USER_ID = :id";
    private static final String QUERY_FIND_BY_EMAIL = "SELECT USER_ID,NICKNAME,PASSWORD,EMAIL FROM USERS WHERE EMAIL = ?";
    private static final String QUERY_CONTAINS_EMAIL = "SELECT count(EMAIL) FROM USERS WHERE EMAIL = ?";
    private static final String QUERY_FIND_ALL_USERS = "SELECT USER_ID, NICKNAME, EMAIL,PASSWORD FROM USERS";
    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public UserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public int save(User user) {
        try {
            SqlParameterSource sqlParameterSource = new MapSqlParameterSource(getParameters(user));
            KeyHolder keyHolder = new GeneratedKeyHolder();
            namedParameterJdbcTemplate.update(QUERY_SAVE, sqlParameterSource, keyHolder);
            return (int) keyHolder.getKey();
        } catch (DataAccessException e) {
            logger.debug("[ Message = {} ][ Nickname = {} ][ Email = {} ][ Password = {} ]",
                    SAVE_USER_FAILED_CODE.getMessage(),
                    user.getNickname(),
                    user.getEmail(),
                    user.getPassword());
            throw new SaveUserFailedException(SAVE_USER_FAILED_CODE);
        }
    }

    private static Map<String, Object> getParameters(User user) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("email", user.getEmail());
        parameters.put("nickname", user.getNickname());
        parameters.put("password", user.getPassword());
        return parameters;
    }

    public List<User> getAllUsers() {
        try {
            return jdbcTemplate.query(QUERY_FIND_ALL_USERS, getUserRowMapper());
        } catch (DataAccessException e) {
            logger.debug("Message = {}", GET_ALL_USERS_FAILED_CODE.getMessage());
            throw new GetAllUsersFailedException(GET_ALL_USERS_FAILED_CODE);
        }
    }

    public Optional<User> findById(Long userId) {
        try {
            SqlParameterSource namedParameters = new MapSqlParameterSource("id", userId);
            User value = namedParameterJdbcTemplate.queryForObject(QUERY_FIND_BY_ID, namedParameters, getUserRowMapper());
            return Optional.ofNullable(value);
        } catch (DataAccessException e) {
            logger.info("[ Message = {} ][ UserId = {} ]", NO_SUCH_USER_ID_CODE.getMessage(), getUserRowMapper());
            return Optional.empty();
        }
    }

    public Optional<User> findByEmail(String email) {
        try {
            return Optional.ofNullable(this.jdbcTemplate.queryForObject(QUERY_FIND_BY_EMAIL, getUserRowMapper(), email));
        } catch (DataAccessException e) {
            logger.info("[ Message = {} ][ Email = {} ]", NO_SUCH_EMAIL_CODE.getMessage(), email);
            return Optional.empty();
        }
    }

    public boolean containsEmail(String email) {
        Integer count = jdbcTemplate.queryForObject(QUERY_CONTAINS_EMAIL, Integer.class, email);
        return count != null && count != 0;
    }

    public void update(User user) {
        try {
            jdbcTemplate.update(QUERY_UPDATE, user.getNickname(), user.getEmail(),
                    user.getId());
        } catch (DataAccessException e) {
            logger.debug("[ Message = {} ][ Id = {} ][ Nickname = {} ][ Email = {} ]",
                    UPDATE_USER_FAILED_CODE.getMessage(), user.getId(), user.getNickname(), user.getEmail());
            throw new UpdateUserFailedException(UPDATE_USER_FAILED_CODE);
        }
    }

    private static RowMapper<User> getUserRowMapper() {
        return (resultSet, rowNum) -> new User.Builder()
                .id(resultSet.getLong(COLUMN_USER_ID))
                .email(resultSet.getString(COLUMN_EMAIL).trim())
                .nickname(resultSet.getString(COLUMN_NICKNAME).trim())
                .password(resultSet.getString(COLUMN_PASSWORD).trim())
                .build();
    }
}
