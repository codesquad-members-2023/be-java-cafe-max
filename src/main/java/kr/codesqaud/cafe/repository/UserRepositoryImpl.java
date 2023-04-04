package kr.codesqaud.cafe.repository;

import java.util.List;
import javax.sql.DataSource;
import kr.codesqaud.cafe.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * H2 DB에 JdbcTemplate을 사용하여 데이터를 저장하는 저장소
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate template;

    public UserRepositoryImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public User save(User user) {
        String sql = "insert into users(userId, password, name, email) values(?, ?, ?, ?)"; // TODO: 컬럼이 많아질 수록 순서가 헷갈리는데 개선할 수 있는 방법이 있을까
        template.update(
                sql, user.getUserId(), user.getPassword(), user.getName(), user.getEmail()
        );
        return user;
    }

    @Override
    public User findById(String userId) {
        String sql = "select * from users where userId = ?";
        return template.queryForObject(sql, userRowMapper(), userId);
    }

    @Override
    public User findByName(String name) {
        String sql = "select * from users where name = ?";
        return template.queryForObject(sql, userRowMapper(), name);
    }

    @Override
    public List<User> findAll() {
        String sql = "select * from users";
        return template.query(sql, userRowMapper());
    }

    private RowMapper<User> userRowMapper() {
        return (resultSet, rowNumber) -> new User.Builder()
                .sequence(resultSet.getLong("sequence"))
                .userId(resultSet.getString("userId"))
                .password(resultSet.getString("password"))
                .name(resultSet.getString("name"))
                .email(resultSet.getString("email"))
                .build();
    }
}
