package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class JdbcUserRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcUserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate();
    }
    //        @Override
    public User save(User user) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("User_squad").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new ConcurrentHashMap<>();
        parameters.put("userNum", user.getUserNum());  // 지금 가서 getter 만들기
        parameters.put("userId", user.getUserLoginId());
        parameters.put("password", user.getPassword()); // 지금 가서 getter 만들기
        parameters.put("email", user.getEmail()); // 지금 가서 getter 만들기

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        User.setUserId(key.longValue()); // 지금 가서 userId 속성 + setter 만들기 (기존의 userId는 userLoginId로 변경)
        //빨간줄: 'Make User.userId static'이라는데 Article에서는 안 이랬는데 (영문을 모르는 중)
        return user;
    }
    //        @Override
    public Optional<User> findById(Long id) {
        List<User> result = jdbcTemplate.query("select * from user_squad where id = ?",
                userRowMapper(), id);
        return result.stream().findAny();
    }
    //        @Override
    public List<User> findAll() {
        return jdbcTemplate.query("select * from users_squad", userRowMapper());
    }
    public void clearStore() {
        jdbcTemplate.update("delete from users_squad");
    }
    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            User user = new User(); // 없던 기본 생성자 지금 만들기
            user.setUserId(rs.getLong("id"));
            user.setUserNum(rs.getLong("userNum"));
            user.setUserLoginId(rs.getString("userLoginId"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("email"));
            return user;
        };
    }
}
