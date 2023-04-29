package kr.codesqaud.cafe.repository.user;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.global.Tables;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcTemplateUserRepository implements UserRepository {
    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper<User> userRowMapper = BeanPropertyRowMapper.newInstance(User.class);

    public JdbcTemplateUserRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(Tables.USERS)
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public User save(User user) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(user);
        Number key = simpleJdbcInsert.executeAndReturnKey(param);
        user.setId(key.longValue());
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        String sql = "select id, user_id, password, name, email from " + Tables.USERS + " where id = :id";

        try {
            Map<String, Object> param = Map.of("id", id);
            User user = template.queryForObject(sql, param, userRowMapper);
            assert user != null;
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        String sql = "select id, user_id, password, name, email from " + Tables.USERS + " where user_id = :userId";

        try {
            Map<String, Object> param = Map.of("userId", userId);
            User user = template.queryForObject(sql, param, userRowMapper);
            assert user != null;
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        String sql = "select id, user_id, password, name, email from " + Tables.USERS;
        return template.query(sql, userRowMapper);
    }

    public Optional<User> findByLoginId(String loginId) {
        return findAll().stream()
                .filter(u -> u.getUserId().equals(loginId))
                .findFirst();
    }

    @Override
    public void update(Long id, User user) {

        String sql = "update " + Tables.USERS +
                " set password=:password, name=:name, email=:email " +
                "where id=:id";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("password", user.getPassword())
                .addValue("name", user.getName())
                .addValue("email", user.getEmail())
                .addValue("id", id);

        template.update(sql, param);
    }
}
