package kr.codesqaud.cafe.repository.user;

import kr.codesqaud.cafe.domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class JdbcUserRepository implements UserRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcUserRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(final User user) {
        final String sql = "INSERT INTO users (userId, password, name, email) VALUES (:userId, :password, :name, :email)";
        jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(user));
    }

    @Override
    public User findByUserId(final String userId) {
        final String sql = "SELECT userId, password, name, email FROM users WHERE userId = :userId LIMIT 1";
        return jdbcTemplate.queryForObject(sql,
                Map.of("userId", userId),
                BeanPropertyRowMapper.newInstance(User.class));
    }

    @Override
    public List<User> findAll() {
        final String sql = "SELECT userId, password, name, email FROM users";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(User.class));
    }

    @Override
    public boolean exist(final String userId) {
        final String sql = "SELECT userId FROM users WHERE userId = :userId LIMIT 1";
        final Integer count = jdbcTemplate.queryForObject(sql,
                Map.of("userId", userId),
                Integer.class);
        return count > 0;
    }

    @Override
    public void update(final User user) {
        final String sql = "UPDATE users SET name = :name, email = :email, password = :password WHERE userId = :userId";
        Map<String, Object> parameter = Map.of(
                "name", user.getName(),
                "email", user.getEmail(),
                "password", user.getPassword(),
                "userId", user.getUserId());
        jdbcTemplate.update(sql, parameter);
    }
}
