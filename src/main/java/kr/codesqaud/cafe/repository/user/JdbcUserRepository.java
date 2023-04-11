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
//    public JdbcUserRepository(JdbcTemplate jdbcTemplate){
//        this.jdbcTemplate = jdbcTemplate;
//    }
    public JdbcUserRepository(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User save(User user) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("users").usingGeneratedKeyColumns("customerId");

        Map<String, Object> parameters = new ConcurrentHashMap<>();
        parameters.put("userId", user.getUserId());
        parameters.put("password", user.getPassword());
        parameters.put("name", user.getName());
        parameters.put("email", user.getEmail());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        user.setCustomerId(key.longValue());
        return user;
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        // 디자인 패턴 중, 템플릿 메서드 패턴의 요소가 많이 반영되어있기 때문이다.
        List<User> result = jdbcTemplate.query("select * from users where userId = ?", userRowMapper(), userId);
        return result.stream().findAny();
    }

    @Override
    public Optional<User> findByName(String name) {
        List<User> result = jdbcTemplate.query("select * from users where name = ?", userRowMapper(), name);
        return result.stream().findAny();
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("select * from users", userRowMapper());
    }

    @Override
    public void clearStore() {
        jdbcTemplate.update("delete from users");
    }

    private RowMapper<User> userRowMapper(){
        return (rs, rowNum) -> {
            User user = new User();
            user.setCustomerId(rs.getLong("customerId"));
            user.setUserId(rs.getString("userId"));
            user.setPassword(rs.getString("password"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            return user;
        };
    }

//    public RowMapper<User> userRowMapper() {
//        return BeanPropertyRowMapper.newInstance(User.class);
//    }

}
