package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;
import java.util.stream.Stream;

@Repository
public class JdbcTemplateUserRepository implements UserRepository {

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JdbcTemplateUserRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("USER_TB")
                .usingGeneratedKeyColumns("ID");
    }

    @Override
    public void join(User user) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(user);
        Number key = simpleJdbcInsert.executeAndReturnKey(param);
        user.create(key.longValue(), user);
    }

    @Override
    public void update(User updateUser) {
        String sql = "UPDATE USER_TB SET NAME = :NAME, PASSWORD = :PASSWORD, EMAIL = :EMAIL WHERE ID = :ID";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("NAME", updateUser.getName())
                .addValue("PASSWORD", updateUser.getPassword())
                .addValue("EMAIL", updateUser.getEmail())
                .addValue("ID", updateUser.getId());

        template.update(sql, param);
    }
    @Override
    public Optional<User> findById(long id) {
        String sql = "SELECT * FROM USER_TB WHERE ID = :ID";
        Map<String, Object> param = Map.of("ID", id);
        try (Stream<User> result = template.queryForStream(sql, param, userRowMapper())) {
            return result.findFirst();
        }
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        String sql = "SELECT * FROM USER_TB WHERE USERID = :USERID";
        Map<String, Object> param = Map.of("USERID", userId);
        try (Stream<User> result = template.queryForStream(sql, param, userRowMapper())) {
            return result.findFirst();
        }
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM USER_TB";
        return template.query(sql, userRowMapper());
    }

    public RowMapper<User> userRowMapper() {
        return BeanPropertyRowMapper.newInstance(User.class);
    }
}
