package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class JdbcUserRepository implements UserRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(User user) {
        String sql = "insert into userTable(userId, password, userName, email) values(?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getUserId(), user.getPassword(), user.getUserName(), user.getEmail());
    }

    @Override
    public List<User> getAllUsers() {
        return jdbcTemplate.query("select * from userTable", userRowMapper());
    }

    @Override
    public User getSpecificUser(String userId) {
        return null;
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            return new User(rs.getString("userId"),
                    rs.getString("password"),
                    rs.getString("userName"),
                    rs.getString("email"));
        };
    }
}
