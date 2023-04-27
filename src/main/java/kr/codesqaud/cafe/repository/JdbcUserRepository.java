package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class JdbcUserRepository {
//        implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcUserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
//    @Override
    public void save(User user) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("Users_squad").usingGeneratedKeyColumns("userId");
        Map<String, Object> parameters = new ConcurrentHashMap<>();
        parameters.put("userNum", user.getUserNum());
        parameters.put("userLoginId", user.getUserLoginId());
        parameters.put("password", user.getPassword());
        parameters.put("email", user.getEmail());
        Number key = jdbcInsert.executeAndReturnKey(parameters);
        long longKey = key.longValue();
        user.setUserId(longKey);
    }

//    @Override
    public Optional<User> getUserByUserId(Long userId) {
        List<User> result = jdbcTemplate.query("select * from users_squad where userId = ?", userRowMapper(), userId);
        return result.stream().findAny();
    }

//    @Override
    public List<User> getUserList() {
        return jdbcTemplate.query("select * from users_squad", userRowMapper());
    }

//    @Override
    public void clearStore() {
        jdbcTemplate.update("delete from users_squad");
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            User user = new User();
            user.setUserId(rs.getLong("userId"));
            user.setUserNum(rs.getLong("userNum"));
            user.setUserLoginId(rs.getString("userLoginId"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("email"));
            return user;
        };
    }
}
