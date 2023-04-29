package kr.codesqaud.cafe.user.repository;

import kr.codesqaud.cafe.user.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcUserRepository implements UserRepository {
    private final JdbcTemplate jdbcTemplate;


    private final RowMapper<User> userRowMapper = (rs, rowNum) -> new User.Builder()
            .id(rs.getString("id"))
            .name(rs.getString("name"))
            .password(rs.getString("password"))
            .email(rs.getString("email"))
            .build();

    public JdbcUserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public User save(User user) {
        jdbcTemplate.update("INSERT INTO USERS VALUES (?, ?, ?, ?)",
                user.getId(),
                user.getName(),
                user.getPassword(),
                user.getEmail()
        );
        return user;
    }

    @Override
    public Optional<User> findById(String id) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject("SELECT * FROM USERS WHERE ID = ?", userRowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByName(String name) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject("SELECT * FROM USERS WHERE NAME = ?", userRowMapper, name));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM USERS", userRowMapper);
    }

    @Override
    public void clearStore() {
    }
}
