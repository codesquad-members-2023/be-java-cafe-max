package kr.codesqaud.cafe.repository;

import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import kr.codesqaud.cafe.domain.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 * H2 DB에 JdbcTemplate을 사용하여 데이터를 저장하는 저장소
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final NamedParameterJdbcTemplate template;

    public UserRepositoryImpl(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public User save(User user) {
        String sql = "insert into users (userId, password, name, email) values (:userId, :password, :name, :email)";
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("userId", user.getUserId())
                .addValue("password", user.getPassword())
                .addValue("name", user.getName())
                .addValue("email", user.getEmail());
        template.update(sql, param);
        return user;
    }

    @Override
    public Optional<User> findById(String userId) {
        String sql = "select * from users where userId = ?";
        try {
            return Optional.ofNullable(template.queryForObject(sql, userRowMapper(), userId));
        } catch (EmptyResultDataAccessException e) { // TODO: 해당 처리를 안하면 DB에 해당 데이터가 없을 때 예외가 발생한다. 이렇게 하는것이 맞는지 모르겠다.
            return Optional.ofNullable(null);
        }
    }

    @Override
    public Optional<User> findByName(String name) {
        String sql = "select * from users where name = ?";
        return Optional.ofNullable(template.queryForObject(sql, userRowMapper(), name));
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
