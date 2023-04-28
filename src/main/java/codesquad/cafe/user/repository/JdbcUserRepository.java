package codesquad.cafe.user.repository;

import codesquad.cafe.user.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcUserRepository implements UserRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert insertAction;

    public JdbcUserRepository(final DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.insertAction = new SimpleJdbcInsert(dataSource)
                .withTableName("users");
    }

    @Override
    public User save(final User user) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(user);
        insertAction.execute(params);
        return user;
    }

    @Override
    public Optional<User> findById(final String id) {
        String sql = "SELECT * FROM users WHERE id = :id";
        SqlParameterSource params = new MapSqlParameterSource("id", id);
        try {
            User user = namedParameterJdbcTemplate.queryForObject(sql, params,
                    (rs, rowNum) -> new User(
                            rs.getString("id")
                            , rs.getString("password")
                            , rs.getString("name")
                            , rs.getString("email")));
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        String sql = "select * from users";
        return namedParameterJdbcTemplate.query(sql,
                (rs, rowNum) -> new User(
                        rs.getString("id")
                        , rs.getString("password")
                        , rs.getString("name")
                        , rs.getString("email")));
    }

    @Override
    public void update(final User user) {
        String sql = "update users set password = :password, name = :name, email = :email where id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", user.getId());
        params.addValue("password", user.getPassword());
        params.addValue("name", user.getName());
        params.addValue("email", user.getEmail());

        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public String findNameById(final String id) {
        String sql = "select name from users where id = :id";
        SqlParameterSource params = new MapSqlParameterSource("id", id);
        return namedParameterJdbcTemplate.queryForObject(sql, params, String.class);
    }
}
