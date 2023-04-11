package kr.codesqaud.cafe.user.repository;

import kr.codesqaud.cafe.user.domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserJdbcRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final BeanPropertyRowMapper<User> userRowMapper = BeanPropertyRowMapper.newInstance(User.class);

    public UserJdbcRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public String save(User user) {
        jdbcTemplate.update("INSERT INTO users (user_id, password, user_name, email) VALUES (:userId, :password, :userName, :email)",
                new BeanPropertySqlParameterSource(user));
        return user.getUserId();
    }

    public boolean containsUserId(String userId) {
        Map<String, String> namedParameters = Collections.singletonMap("user_id", userId);
        Optional<Integer> countOfUser = Optional.ofNullable(jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM users WHERE user_id = :user_id", namedParameters, Integer.class));
        return countOfUser.orElse(0) > 0;
    }

    public User findByUserId(String userId) {
        Map<String, String> namedParameters = Collections.singletonMap("user_id", userId);
        return jdbcTemplate.queryForObject("SELECT user_id, password, user_name, email FROM users WHERE user_id = :user_id",
                namedParameters, userRowMapper);
    }

    public List<User> findAll() {
        return jdbcTemplate.query("SELECT user_id, password, user_name, email FROM users", userRowMapper);
    }

}
