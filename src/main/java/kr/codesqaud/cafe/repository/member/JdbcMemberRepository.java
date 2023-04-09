package kr.codesqaud.cafe.repository.member;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.domain.Member;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Primary
@Repository
public class JdbcMemberRepository implements MemberRepository {
    private final RowMapper<Member> memberRowMapper = (rs, rowNum) -> {
        Long memberId = rs.getLong("memberId");
        String email = rs.getString("email");
        String password = rs.getString("password");
        String nickname = rs.getString("nickName");
        LocalDateTime createDate = rs.getTimestamp("create_date").toLocalDateTime();
        return new Member(memberId, email, password, nickname, createDate);
    };

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcMemberRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(Member member) {
        String sql = "INSERT INTO member(email, password, nickName, create_date) VALUES(:email, :password, :nickName, :createDate)";
        SqlParameterSource parameter = new BeanPropertySqlParameterSource(member);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, parameter, keyHolder);
        Number key = Objects.requireNonNull(keyHolder.getKey());
        return key.longValue();
    }


    @Override
    public Optional<Member> findById(Long memberId) {
        String sql = "SELECT memberId, email, password, nickName, create_date FROM member WHERE memberId = :memberId";
        SqlParameterSource parameter = new MapSqlParameterSource("memberId", memberId);
        return Optional.ofNullable(DataAccessUtils.singleResult(jdbcTemplate.query(sql, parameter, memberRowMapper)));
    }


    @Override
    public List<Member> findAll() {
        String sql = "SELECT memberId, email, password, nickName, create_date FROM member";
        return jdbcTemplate.query(sql, memberRowMapper);
    }

    @Override
    public void update(Member member) {
        String sql = "UPDATE member SET email = :email, password = :password, nickName = :nickName WHERE memberId = :memberId";
        SqlParameterSource parameter = new BeanPropertySqlParameterSource(member);
        jdbcTemplate.update(sql, parameter);
    }

    @Override
    public void deleteById(Long memberId) {
        String sql = "DELETE FROM member WHERE memberId = :memberId";
        SqlParameterSource parameter = new MapSqlParameterSource("memberId", memberId);
        jdbcTemplate.update(sql, parameter);
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM member";
        jdbcTemplate.update(sql, (SqlParameterSource) null);
    }

//    public static String toString(Number value) {
//        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
//        buffer.putLong(value.longValue());
//        byte[] bytes = buffer.array();
//        return String.nameStringFromBytes(bytes);
//    }
}
