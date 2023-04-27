package kr.codesqaud.cafe.user;

import kr.codesqaud.cafe.article.ArticleDTO;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
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
    public List<UserDTO> findAll() {
        final String SQL = "SELECT * FROM Users";
        return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(UserDTO.class));
    }

    @Override
    public UserDTO findById(String userId) {
        SqlParameterSource namedParameterSource = new MapSqlParameterSource().addValue("userId", userId);
        final String SQL = "SELECT * FROM Users WHERE userId = :userId";
        return jdbcTemplate.queryForObject(SQL, namedParameterSource, new BeanPropertyRowMapper<>(UserDTO.class));
    }

    @Override
    public void update(User updatedUser) {

    }
}
