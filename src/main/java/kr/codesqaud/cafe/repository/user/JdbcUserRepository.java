package kr.codesqaud.cafe.repository.user;

import kr.codesqaud.cafe.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class JdbcUserRepository implements UserRepository{

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JdbcUserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("member")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public void save(final User user) {
        Map<String, Object> parameters = new ConcurrentHashMap<>();
        parameters.put("id", user.getId());
        parameters.put("password", user.getPassword());
        parameters.put("name", user.getName());
        parameters.put("email",user.getEmail());

        Number key = simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        user.setId(key.toString());

    }

    @Override
    public Optional<User> findById(final String id) {
        String sql = "select * from member where id = ?";
        return jdbcTemplate.query(sql, userRowMapper, id).stream().findAny();
    }

    @Override
    public Optional<User> findByEmail(final String email) {
        String sql = "select * from member where email = ?";
        return jdbcTemplate.query(sql, userRowMapper, email).stream().findAny();
    }

    @Override
    public List<User> findAllUser() {
        String sql = "SELECT id, password, name, email  FROM member";
        return jdbcTemplate.query(sql, userRowMapper);
    }

    private RowMapper<User> userRowMapper =  (rs, rowNum) ->
            new User(rs.getString("id"),rs.getString("password"),
                    rs.getString("name"),rs.getString("email"));
}
