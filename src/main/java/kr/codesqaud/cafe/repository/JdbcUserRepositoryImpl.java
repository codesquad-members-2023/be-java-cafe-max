package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Primary
@Repository
public class JdbcUserRepositoryImpl implements UserRepository {

    private final NamedParameterJdbcTemplate template;

    public JdbcUserRepositoryImpl(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Long save(final User user) {
        final String sql = "INSERT INTO users (username, nickname, password, email) " +
                "VALUES (:username, :nickname, :password, :email)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, new BeanPropertySqlParameterSource(user), keyHolder);

        return (long) Objects.requireNonNull(keyHolder.getKeys()).get("id");
    }

    @Override
    public Optional<User> findById(final Long id) {
        final String sql  = "SELECT * FROM users WHERE id = :id";

        try (final Stream<User> result = template.queryForStream(sql, Map.of("id", id), userRowMapper())) {
            return result.findFirst();
        }
    }

    @Override
    public Optional<User> findByUsername(final String username) {
        final String sql  = "SELECT * FROM users WHERE username = :username";

        try (final Stream<User> result = template.queryForStream(sql, Map.of("username", username), userRowMapper())) {
            return result.findFirst();
        }
    }

    @Override
    public Optional<User> findByEmail(final String email) {
        final String sql  = "SELECT * FROM users WHERE email = :email";

        try (final Stream<User> result = template.queryForStream(sql, Map.of("email", email), userRowMapper())) {
            return result.findFirst();
        }
    }

    @Override
    public List<User> findAll() {
        final String sql = "SELECT * FROM users";

        return template.query(sql, userRowMapper());
    }

    @Override
    public void update(User user) {
        final String sql = "UPDATE users SET nickname=:nickname, password=:password WHERE id=:id";

        final MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("nickname", user.getNickname())
                .addValue("password", user.getPassword())
                .addValue("id", user.getId());

        template.update(sql, param);
    }

    @Override
    public boolean existsUsername(String username) {
        final String sql = "SELECT EXISTS (SELECT 1 FROM users WHERE username = :username)";

        return Boolean.TRUE.equals(template.queryForObject(sql, Map.of("username", username), Boolean.class));
    }

    @Override
    public boolean existsNickname(String nickname) {
        final String sql = "SELECT EXISTS (SELECT 1 FROM users WHERE nickname = :nickname)";

        return Boolean.TRUE.equals(template.queryForObject(sql, Map.of("nickname", nickname), Boolean.class));
    }

    private RowMapper<User> userRowMapper() {
        return ((rs, rowNum) -> new User(
                rs.getLong("id"),
                rs.getString("username"),
                rs.getString("nickname"),
                rs.getString("password"),
                rs.getString("email"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
        ));
    }

}
