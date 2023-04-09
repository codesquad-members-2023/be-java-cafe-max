package kr.codesqaud.cafe.account;

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


@Repository
public class UserRepository {

    private static final String COLUMN_USER_ID = "USER_ID";
    private static final String COLUMN_PASSWORD = "PASSWORD";
    private static final String COLUMN_NICKNAME = "NICKNAME";
    private static final String COLUMN_EMAIL = "EMAIL";
    private static final String QUERY_UPDATE = "UPDATE ACCOUNT SET NICKNAME = ?, EMAIL = ? WHERE USER_ID = ?";
    private static final String QUERY_SAVE = "INSERT INTO ACCOUNT (NICKNAME, EMAIL, PASSWORD) values ( :NICKNAME,:EMAIL,:PASSWORD )";
    private static final String QUERY_FIND_BY_ID = "SELECT USER_ID,EMAIL,NICKNAME,PASSWORD FROM ACCOUNT WHERE USER_ID = :USER_ID";
    private static final String QUERY_FIND_BY_EMAIL = "SELECT USER_ID,NICKNAME,PASSWORD,EMAIL FROM ACCOUNT WHERE EMAIL = ?";
    private static final String QUERY_CONTAINS_EMAIL = "SELECT count(EMAIL) FROM ACCOUNT WHERE EMAIL = ?";
    private static final String QUERY_FIND_ALL_USERS = "SELECT USER_ID, NICKNAME, EMAIL,PASSWORD FROM ACCOUNT";

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public UserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public int save(User user) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(getParameters(user));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(QUERY_SAVE, sqlParameterSource, keyHolder);
        return (int) keyHolder.getKey();
    }

    private static Map<String, Object> getParameters(User user) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(COLUMN_EMAIL, user.getEmail());
        parameters.put(COLUMN_NICKNAME, user.getNickname());
        parameters.put(COLUMN_PASSWORD, user.getPassword());
        return parameters;
    }

    public List<User> getAllUsers() {
        return jdbcTemplate.query(QUERY_FIND_ALL_USERS, getUserRowMapper());
    }

    public User findById(Long userId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource(COLUMN_USER_ID, userId);
        return namedParameterJdbcTemplate.queryForObject(QUERY_FIND_BY_ID, namedParameters, getUserRowMapper());
    }

    public Optional<User> findByEmail(String email) {
        try {
            return Optional.ofNullable(this.jdbcTemplate.queryForObject(QUERY_FIND_BY_EMAIL, getUserRowMapper(), email));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    public boolean containsEmail(String email) {
        Integer count = jdbcTemplate.queryForObject(QUERY_CONTAINS_EMAIL, Integer.class, email);
        return count != null && count != 0;
    }

    public void update(User user) {
        jdbcTemplate.update(QUERY_UPDATE, user.getNickname(), user.getEmail(), user.getId());
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
