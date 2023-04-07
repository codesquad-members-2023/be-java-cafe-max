package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcTemplateUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateUserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User save(User user) {
        String sql = "insert into users (userId, password, name, email) values (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                user.getUserId(),
                user.getPassword(),
                user.getName(),
                user.getEmail());
        return user;
    }

    @Override
    public Optional<User> findById(String id) {
        String sql = "select * from users where userId = ?";
        List<User> userList = jdbcTemplate.query(sql, userRowMapper(), id);
        return userList.stream().findAny();
    }

    @Override
    public Optional<User> findByName(String name) {
        String sql = "select * from users where name = ?";
        List<User> userList = jdbcTemplate.query(sql, userRowMapper(), name);
        return userList.stream().findAny();
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("select * from users", userRowMapper());
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            User user = new User(rs.getString("userId"),
                    rs.getString("password"),
                    rs.getString("name"),
                    rs.getString("email"));
            return user;
        };
    }
}
