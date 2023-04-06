package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class JdbcTemplateUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateUserRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void join(User user) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("USER_TB").usingGeneratedKeyColumns("ID");

        Map<String, Object> userParameters = new HashMap<>();
        userParameters.put("USERID", user.getUserId());
        userParameters.put("PASSWORD", user.getPassword());
        userParameters.put("NAME", user.getName());
        userParameters.put("EMAIL", user.getEmail());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(userParameters));
        user.setId(key.longValue());
    }

    @Override
    public Optional<User> findById(long id) {
        List<User> wantedUser = jdbcTemplate.query("SELECT * FROM USER_TB WHERE ID = ?", userRowMapper(), id);
        return wantedUser.stream().findAny();
    }

    @Override
    public Optional<User> findByName(String userId) {
        List<User> wantedUser = jdbcTemplate.query("SELECT * FROM USER_TB WHERE USERID = ?", userRowMapper(), userId);
        return wantedUser.stream().findAny();
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM USER_TB", userRowMapper());
    }

    public RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("ID"));
            user.setUserId(rs.getString("NAME"));
            user.setPassword(rs.getString("PASSWORD"));
            user.setEmail(rs.getString("EMAIL"));
            return user;
        };
    }
}
