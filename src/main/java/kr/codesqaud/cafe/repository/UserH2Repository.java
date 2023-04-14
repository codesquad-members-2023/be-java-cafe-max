package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.entity.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserH2Repository implements UserRepository{

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserH2Repository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(User user) {
        jdbcTemplate.update(
                "INSERT INTO \"user\"(userId, password, name, email) VALUES (:userId, :password, :name, :email)",
                new BeanPropertySqlParameterSource(user)
        );
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM \"user\"", userRowMapper());
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        List<User> result = jdbcTemplate.query("SELECT * FROM \"user\" WHERE userId = :userId", userRowMapper());
        return result.stream().findAny();
    }


    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> new User(
                rs.getString("userId"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getString("email")
        );
    }
}
