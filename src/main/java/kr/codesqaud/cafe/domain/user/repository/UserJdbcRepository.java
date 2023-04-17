package kr.codesqaud.cafe.domain.user.repository;

import kr.codesqaud.cafe.domain.user.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
    public Optional<User> findById(String id) { // EmptyResultDataAccessException 때문에 query 사용
        List<User> users = jdbcTemplate.query(
                "SELECT IDX , ID , PASSWORD , NAME , EMAIL FROM USERS WHERE ID = ?", rowMapper(), id
        );
        return users.stream().findFirst();
    }

    private RowMapper<User> rowMapper() {
        return (rs, rowNum) ->
                new User(rs.getInt("IDX"), rs.getString("ID"),
                        rs.getString("PASSWORD"), rs.getString("NAME"),
                        rs.getString("EMAIL"));
    }

    @Override
    public void update(User user) {
        jdbcTemplate.update("UPDATE USERS set PASSWORD = ? , NAME = ? , EMAIL = ? WHERE ID = ?",
                user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
    }


    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(
                "SELECT IDX , ID , PASSWORD , NAME , EMAIL  FROM USERS", rowMapper()
        );
    }
}
