package kr.codesqaud.cafe.repository.user;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.exception.user.UserNotFoundException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class JdbcUserRepository implements UserRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<User> userRowMapper = BeanPropertyRowMapper.newInstance(User.class);

    public JdbcUserRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String save(User user) {
        final String sql = "INSERT INTO users (userId, password, name, email) VALUES (:userId, :password, :name, :email)";
        jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(user));
        return user.getUserId();
    }

    @Override
    public User findByUserId(String userId) {
        final String sql = "SELECT userId, password, name, email FROM users WHERE userId = :userId LIMIT 1";
        return jdbcTemplate.queryForStream(sql, Map.of("userId", userId), userRowMapper)
                .findFirst()
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public List<User> findAll() {
        final String sql = "SELECT userId, password, name, email FROM users";
        return jdbcTemplate.query(sql, userRowMapper);
    }

    @Override
    public boolean exist(String userId) {
        final String sql = "SELECT count(*) FROM users WHERE userId = :userId LIMIT 1";
        final Integer count = jdbcTemplate.queryForObject(sql,
                Map.of("userId", userId),
                Integer.class);
        return count > 0;
    }

    @Override
    public int update(User user) {
        final String sql = "UPDATE users SET name = :name, email = :email, password = :password WHERE userId = :userId";
        Map<String, Object> parameter = Map.of(
                "name", user.getName(),
                "email", user.getEmail(),
                "password", user.getPassword(),
                "userId", user.getUserId());
        return jdbcTemplate.update(sql, parameter);
    }
}
