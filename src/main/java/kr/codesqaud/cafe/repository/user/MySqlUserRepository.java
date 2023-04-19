package kr.codesqaud.cafe.repository.user;

import kr.codesqaud.cafe.domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class MySqlUserRepository implements UserRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<User> userRowMapper = BeanPropertyRowMapper.newInstance(User.class);

    public MySqlUserRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String save(User user) {
        final String sql = "INSERT INTO user (userId, password, name, email) VALUES (:userId, :password, :name, :email)";
        jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(user));
        return user.getUserId();
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        final String sql = "SELECT userId, password, name, email FROM user WHERE userId = :userId LIMIT 1";
        try (final Stream<User> result = jdbcTemplate.queryForStream(sql, Map.of("userId", userId), userRowMapper)) {
            return result.findFirst();
        }
    }

    @Override
    public List<User> findAll() {
        final String sql = "SELECT userId, password, name, email FROM user";
        return jdbcTemplate.query(sql, userRowMapper);
    }

    @Override
    public boolean exist(String userId) {
        final String sql = "SELECT count(*) FROM user WHERE userId = :userId LIMIT 1";
        final Integer count = jdbcTemplate.queryForObject(sql,
                Map.of("userId", userId),
                Integer.class);
        return count > 0;
    }

    @Override
    public int update(User user) {
        final String sql = "UPDATE user SET name = :name, email = :email, password = :password WHERE userId = :userId";
        Map<String, Object> parameter = Map.of(
                "name", user.getName(),
                "email", user.getEmail(),
                "password", user.getPassword(),
                "userId", user.getUserId());
        return jdbcTemplate.update(sql, parameter);
    }

    @Override
    public boolean existByName(String name) {
        final String sql = "SELECT EXISTS(SELECT 1 FROM user WHERE name = :name LIMIT 1)";
        final int count = jdbcTemplate.queryForObject(sql,
                Map.of("name", name),
                Integer.class);
        return count > 0;
    }
}
