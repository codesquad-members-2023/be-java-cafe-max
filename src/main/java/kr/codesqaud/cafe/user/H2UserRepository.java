package kr.codesqaud.cafe.user;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class H2UserRepository implements UserRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public H2UserRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void save(User user) {
        final String SQL = "INSERT INTO Users (userId, password, name, email) VALUES (:userId, :password, :name, :email)";
        jdbcTemplate.update(SQL, new BeanPropertySqlParameterSource(user));
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User findById(String userId) {
        return null;
    }

    @Override
    public void update(User updatedUser) {

    }
}
