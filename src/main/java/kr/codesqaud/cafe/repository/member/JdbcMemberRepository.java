package kr.codesqaud.cafe.repository.member;

import java.sql.ResultSetMetaData;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.domain.Member.MemberBuilder;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcMemberRepository implements MemberRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcMemberRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(Member member) {
        String sql = "INSERT INTO member(email, password, nickname, create_date) "
                   + "VALUES(:email, :password, :nickname, :createDateTime)";
        SqlParameterSource parameter = new BeanPropertySqlParameterSource(member);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, parameter, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public Optional<Member> findById(Long id) {
        String sql = "SELECT id, email, password, nickname, create_date "
                     + "FROM member "
                    + "WHERE id = :id";
        SqlParameterSource parameter = new MapSqlParameterSource("id", id);
        return Optional.ofNullable(DataAccessUtils.singleResult(jdbcTemplate.query(sql, parameter, memberRowMapper)));
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        String sql = "SELECT id, email, password, nickname, create_date "
                     + "FROM member "
                    + "WHERE email = :email";
        SqlParameterSource parameter = new MapSqlParameterSource("email", email);
        return Optional.ofNullable(DataAccessUtils.singleResult(jdbcTemplate.query(sql, parameter, memberRowMapper)));
    }

    @Override
    public Optional<Member> findByEmailAndPassword(String email, String password) {
        String sql = "SELECT id, email "
                    + "FROM member "
                   + "WHERE email = :email "
                     + "AND password = :password";
        MapSqlParameterSource parameter = new MapSqlParameterSource("email", email);
        parameter.addValue("password", password);
        return Optional.ofNullable(DataAccessUtils.singleResult(jdbcTemplate.query(sql, parameter, memberRowMapper)));
    }

    @Override
    public List<Member> findAll() {
        String sql = "SELECT id, email, password, nickname, create_date "
            + "FROM member";
        return jdbcTemplate.query(sql, memberRowMapper);
    }

    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT EXISTS(SELECT 1 FROM member WHERE email = :email)";
        MapSqlParameterSource parameter = new MapSqlParameterSource("email", email);
        return jdbcTemplate.queryForObject(sql, parameter, Boolean.class);
    }

    @Override
    public boolean existsByEmailAndIdNot(String email, Long id) {
        String sql = "SELECT EXISTS(SELECT 1 FROM member WHERE email = :email AND id != :id)";
        MapSqlParameterSource parameter = new MapSqlParameterSource("email", email);
        parameter.addValue("id", id);
        return jdbcTemplate.queryForObject(sql, parameter, Boolean.class);
    }

    @Override
    public void update(Member member) {
        String sql = "UPDATE member "
                      + "SET email = :email, "
                          + "password = :password, "
                          + "nickname = :nickname "
                    + "WHERE id = :id";
        SqlParameterSource parameter = new BeanPropertySqlParameterSource(member);
        jdbcTemplate.update(sql, parameter);
    }

    private final RowMapper<Member> memberRowMapper = (rs, rowNum) -> {
        MemberBuilder builder = Member.builder();
        ResultSetMetaData metaData = rs.getMetaData();

        for (int i= 1; i <= metaData.getColumnCount(); i++) {
            String columnName = metaData.getColumnName(i).toLowerCase();

            switch (columnName) {
                case "id":
                    builder.id(rs.getLong(columnName));
                    break;
                case "email":
                    builder.email(rs.getString(columnName));
                    break;
                case "password":
                    builder.password(rs.getString(columnName));
                    break;
                case "nickname":
                    builder.nickname(rs.getString(columnName));
                    break;
                case "create_date":
                    builder.createDate(rs.getTimestamp(columnName).toLocalDateTime());
                    break;
            }
        }

        return builder.build();
    };
}
