package kr.codesqaud.cafe.repository.post;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.domain.Post;

@Primary
@Repository
public class JdbcPostRepository implements PostRepository {
    private final RowMapper<Post> postRowMapper = (rs, rowNum) -> {
        Long postId = rs.getLong("postId");
        String title = rs.getString("title");
        String content = rs.getString("content");
        String writerEmail = rs.getString("writerEmail");
        LocalDateTime writeDate = rs.getTimestamp("write_date").toLocalDateTime();
        Long views = rs.getLong("views");
        return new Post(postId, title, content, writerEmail, writeDate, views);
    };

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcPostRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(Post post, Member member) {
        String sql = "INSERT INTO post(title,content,writerEmail,write_date,views) VALUES(:title, :content, :writerEmail,:writeDate,:views)";
        SqlParameterSource parameter = new BeanPropertySqlParameterSource(post);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, parameter, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public Optional<Post> findById(Long postId) {
        String sql = "SELECT postId,title,content,writerEmail,write_date,views FROM post WHERE postId=:postId";
        SqlParameterSource parameter = new MapSqlParameterSource("postId", postId);
        List<Post> posts = jdbcTemplate.query(sql, parameter, postRowMapper);
        return posts.stream().findFirst();
    }

    @Override
    public List<Post> findPostByWriterEmail(String writerEmail) {
        String sql = "SELECT postId,title,content,writerEmail,write_date,views FROM post WHERE writerEmail = :writerEmail ORDER BY write_date";
        SqlParameterSource parameter = new MapSqlParameterSource("writerEmail", writerEmail);
        return jdbcTemplate.query(sql, parameter, postRowMapper);
    }

    @Override
    public List<Post> findAll() {
        String sql = "SELECT postId, title, content,writerEmail,write_date,views,FROM post";
        return jdbcTemplate.query(sql, postRowMapper);
    }

    @Override
    public void update(Post post) {
        String sql = "UPDATE post SET title=:title, content=:content, views=:views WHERE postId = :postId";
        SqlParameterSource parameter = new BeanPropertySqlParameterSource(post);
        jdbcTemplate.update(sql, parameter);
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM post";
        jdbcTemplate.update(sql, (SqlParameterSource) null);
    }
}
