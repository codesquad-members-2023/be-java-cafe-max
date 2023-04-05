package kr.codesqaud.cafe.user.repository;

import kr.codesqaud.cafe.user.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserJdbcRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public String add(User user) {
        jdbcTemplate.update("INSERT INTO users (userid, password, username, email) VALUES (?, ?, ?, ?)",
                user.getUserId(), user.getPassword(), user.getUserName(), user.getEmail());
        return user.getUserId();
    }

    public boolean containsUserId(String userId) {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM users WHERE userid = ?", Integer.class, userId) > 0;
    }

    public User findByUserId(String userId) {
        return jdbcTemplate.queryForObject("SELECT userid, password, username, email FROM users WHERE userid = ?",
                userRowMapper,
                userId);
    }

    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM users", userRowMapper);
    }

    private RowMapper<User> userRowMapper = (resultSet, rowNum) -> {
        return new User(resultSet.getString("userid"),
                resultSet.getString("password"),
                resultSet.getString("username"),
                resultSet.getString("email"));
    };

}
