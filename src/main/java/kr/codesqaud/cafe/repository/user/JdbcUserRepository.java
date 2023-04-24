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

    public JdbcUserRepository(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User save(User user) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("`user`").usingGeneratedKeyColumns("customer_id");

        Map<String, Object> parameters = new ConcurrentHashMap<>();
        parameters.put("user_id", user.getUserId());
        parameters.put("password", user.getPassword());
        parameters.put("name", user.getName());
        parameters.put("email", user.getEmail());
        parameters.put("deleted", user.getDeleted() ? 0 : 1);

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        user.setCustomerId(key.longValue());
        return user;
    }

    private RowMapper<User> userRowMapper(){
        return (rs, rowNum) -> {
            User user = new User();
            user.setCustomerId(rs.getLong("customer_id"));
            user.setUserId(rs.getString("user_id"));
            user.setPassword(rs.getString("password"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setDeleted(rs.getInt("deleted") == 1);
            return user;
        };
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        // 디자인 패턴 중, 템플릿 메서드 패턴의 요소가 많이 반영되어있기 때문이다.
        List<User> result = jdbcTemplate.query("select * from `user` where user_id = ?", userRowMapper(), userId);
        return result.stream().findAny();
    }

    @Override
    public Optional<User> findByName(String name) {
        List<User> result = jdbcTemplate.query("select * from `user` where name = ?", userRowMapper(), name);
        return result.stream().findAny();
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("select * from `user`", userRowMapper());
    }

    @Override
    public void clearStore() {
        jdbcTemplate.update("delete from `user`");
    }
}
