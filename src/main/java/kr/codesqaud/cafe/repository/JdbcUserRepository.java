package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcUserRepository implements UserRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<User> userMapper = (rs, rowNum) -> new User(
            rs.getLong("id"),
            rs.getString("user_id"),
            rs.getString("password"),
            rs.getString("user_name"),
            rs.getString("user_email")
    );

    public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> save(User user) {
        jdbcTemplate.update("insert into users(user_id, password, user_name, user_email) values(?, ?, ?, ?)",
                user.getUserId(),
                user.getPassword(),
                user.getUserName(),
                user.getUserEmail()
        );
        return Optional.of(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?", userMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject("SELECT * FROM users WHERE user_Id = ?", userMapper, userId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM users", userMapper);
    }

    @Override
    public void update(User user) {
        jdbcTemplate.update("UPDATE users SET password = ?, user_name = ?, user_email = ? WHERE id = ?",
                user.getPassword(), user.getUserName(), user.getUserEmail(), user.getId());

    }
}
