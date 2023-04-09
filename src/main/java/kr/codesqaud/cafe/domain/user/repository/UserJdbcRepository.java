package kr.codesqaud.cafe.domain.user.repository;

import kr.codesqaud.cafe.domain.user.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public class UserJdbcRepository implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserJdbcRepository(JdbcTemplate jdbcTemplate) {
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
        return jdbcTemplate.queryForObject(
                "SELECT IDX , ID , PASSWORD , NAME , EMAIL FROM USERS WHERE ID = ?", rowMapper(), id
        );
    }

    private RowMapper<User> rowMapper() {
        return (rs, rowNum) ->
                new User(rs.getInt("IDX"), rs.getString("ID"),
                        rs.getString("PASSWORD"), rs.getString("NAME"),
                        rs.getString("EMAIL"));
    }

    @Override
    public void update(User user) {
        jdbcTemplate.update("update USERS set PASSWORD = ? ,NAME = ? ,EMAIL = ? WHERE ID = ?",
                user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
    }


    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(
                "SELECT IDX , ID , PASSWORD , NAME , EMAIL  FROM USERS", rowMapper()
        );
    }
}
