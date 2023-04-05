package kr.codesqaud.cafe.repository.user;

import kr.codesqaud.cafe.domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
public class H2UserRepository implements UserRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public H2UserRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void save(User user) {
        final String SQL = "INSERT INTO users (userId, password, name, email) VALUES (:userId, :password, :name, :email)";
        jdbcTemplate.update(SQL, new BeanPropertySqlParameterSource(user));
    }

    @Override
    public User findByUserId(String userId) {
        final String SQL = "SELECT * FROM users WHERE userId = :userId";
        return jdbcTemplate.queryForObject(SQL,
                new MapSqlParameterSource().addValue("userId", userId),
                BeanPropertyRowMapper.newInstance(User.class));
    }

    @Override
    public List<User> findAll() {
        final String SQL = "SELECT * FROM users";
        return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(User.class));
    }

    @Override
    public boolean isExists(String userId) {
        final String SQL = "SELECT count(*) FROM users WHERE userId = :userId";
        final Integer count = jdbcTemplate.queryForObject(SQL,
                new MapSqlParameterSource().addValue("userId", userId),
                Integer.class);
        return count > 0;
    }

    @Override
    public void update(User user) {
        final String SQL = "UPDATE users SET name = :name, email = :email, password = :password WHERE userId = :userId";
        Map<String, Object> parameter = Map.of(
                "name", user.getName(),
                "email", user.getEmail(),
                "password", user.getPassword(),
                "userId", user.getUserId());
        jdbcTemplate.update(SQL, parameter);
    }
}
