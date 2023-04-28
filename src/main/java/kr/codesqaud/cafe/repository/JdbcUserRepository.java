package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Primary
@Repository
public class JdbcUserRepository implements UserRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(User user) {
        String sql = "insert into users(userId, password, userName, email) values(?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getUserId(), user.getPassword(), user.getUserName(), user.getEmail());
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("select * from users", userRowMapper());
    }

    @Override
    public User findByUserId(String userId) {
        List<User> result = jdbcTemplate.query("select * from users where userId = ?", userRowMapper(), userId);

        return result.stream().findAny().get();
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            return User.builder()
                    .id(rs.getLong("id"))
                    .userId(rs.getString("userId"))
                    .password(rs.getString("password"))
                    .userName(rs.getString("userName"))
                    .email(rs.getString("email"))
                    .build();
        };
    }
}
