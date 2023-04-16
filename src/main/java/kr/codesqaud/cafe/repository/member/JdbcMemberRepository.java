package kr.codesqaud.cafe.repository.member;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import kr.codesqaud.cafe.domain.Member;
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
                   + "VALUES(:email, :password, :nickName, :createDate)";
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
    public List<Member> findAll() {
        String sql = "SELECT id, email, password, nickname, create_date "
                     + "FROM member";
        return jdbcTemplate.query(sql, memberRowMapper);
    }

    @Override
    public void update(Member member) {
        String sql = "UPDATE member "
                      + "SET email = :email, "
                          + "password = :password, "
                          + "nickname = :nickName "
                    + "WHERE id = :id";
        SqlParameterSource parameter = new BeanPropertySqlParameterSource(member);
        jdbcTemplate.update(sql, parameter);
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM member";
        jdbcTemplate.update(sql, (SqlParameterSource) null);
    }

    private final RowMapper<Member> memberRowMapper = (rs, rowNum) ->
        Member.builder()
            .id(rs.getLong("id"))
            .email(rs.getString("email"))
            .password(rs.getString("password"))
            .nickName(rs.getString("nickname"))
            .createDate(rs.getTimestamp("create_date").toLocalDateTime())
            .build();
}
