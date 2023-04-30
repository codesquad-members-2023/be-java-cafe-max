package kr.codesqaud.cafe.user.repository;

import kr.codesqaud.cafe.user.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository{

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UserRepository(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public String save(User user) {
        String sql = "INSERT INTO user_account (userId, password, name, email) VALUES (:userId, :password, :name, :email)";
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(user);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, sqlParameterSource, keyHolder);
        return user.getUserId();
    }

    public List<User> findAll() {
        String sql = "SELECT userId, password, name, email FROM user_account";
        return namedParameterJdbcTemplate.query(sql, userRowMapper());
    }

    public Optional<User> findById(String userId) {
        String sql = "SELECT userId, password, name, email FROM user_account WHERE userId = :userId";
        SqlParameterSource namedParameters = new MapSqlParameterSource("userId", userId);
        try {
            User user = namedParameterJdbcTemplate.queryForObject(sql, namedParameters, userRowMapper());
            return Optional.of(user);
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            return new User(rs.getString("userId"),rs.getString("password"),
                    rs.getString("name"),rs.getString("email"));
        };
    }
}
