package kr.codesqaud.cafe.domain.member.repository;

import kr.codesqaud.cafe.domain.member.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public class UserJdbcImpl implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(User user) {
        jdbcTemplate.update("INSERT INTO USERS(ID,PASSWORD,NAME,EMAIL) VALUES (?, ?, ?, ?)",
                user.getUserId(),
                user.getPassword(), user.getName(), user.getEmail());
    }

    @Override
    public User findById(String id) {
        List<User> list = jdbcTemplate.query(
                "SELECT * FROM USERS WHERE ID = ?", rowMapper(), id
        );
        return list.get(0);
    }

    private RowMapper<User> rowMapper() {
        return (rs, rowNum) ->
                new User(rs.getInt("IDX"), rs.getString("ID"),
                        rs.getString("PASSWORD"), rs.getString("NAME"),
                        rs.getString("EMAIL"));
    }


    @Override
    public List<User> findAllList() {
        return jdbcTemplate.query(
                "SELECT * FROM USERS", rowMapper()
        );
    }
}
