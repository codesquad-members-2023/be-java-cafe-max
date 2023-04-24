package kr.codesqaud.cafe.user.repository;

import kr.codesqaud.cafe.exception.ResourceNotFoundException;
import kr.codesqaud.cafe.user.domain.User;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserJdbcRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserJdbcRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public String save(User user) {
        jdbcTemplate.update("INSERT INTO users (user_id, password, user_name, email) VALUES (:userId, :password, :userName, :email)",
                new BeanPropertySqlParameterSource(user));
        return user.getUserId();
    }

    public void update(User user) {
        jdbcTemplate.update("UPDATE users SET user_name = :userName, email = :email WHERE user_id = :userId",
                new BeanPropertySqlParameterSource(user));
    }

    public boolean containsUserId(String userId) {
        Map<String, String> namedParameters = Collections.singletonMap("user_id", userId);
        Optional<Integer> countOfUser = Optional.ofNullable(jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM users WHERE user_id = :user_id", namedParameters, Integer.class));
        return countOfUser.orElse(0) > 0;
    }

    public boolean containsUserName(String userName) {
        Map<String, String> namedParameters = Collections.singletonMap("user_name", userName);
        Optional<Integer> countOfUser = Optional.ofNullable(jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM users WHERE user_name = :user_name", namedParameters, Integer.class));
        return countOfUser.orElse(0) > 0;
    }

    public User findByUserId(String userId) {
        Map<String, String> namedParameters = Collections.singletonMap("user_id", userId);
        try {
            return jdbcTemplate.queryForObject("SELECT user_id, password, user_name, email FROM users WHERE user_id = :user_id",
                    namedParameters, userRowMapper);
        } catch (DataRetrievalFailureException e) {
            throw new ResourceNotFoundException("요청한 데이터가 존재하지 않습니다.");
        }
    }

    public List<User> findAll() {
        return jdbcTemplate.query("SELECT user_id, password, user_name, email FROM users", userRowMapper);
    }

    public RowMapper<User> userRowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return User.builder()
                    .userId(rs.getString("user_id"))
                    .password(rs.getString("password"))
                    .userName(rs.getString("user_name"))
                    .email(rs.getString("email"))
                    .build();
        }
    };
}
