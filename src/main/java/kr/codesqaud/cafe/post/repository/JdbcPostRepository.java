package kr.codesqaud.cafe.post.repository;

import kr.codesqaud.cafe.post.service.Post;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class JdbcPostRepository implements PostRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcPostRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Post save(Post post) {
        String sql = "insert into post (writer, title, contents, writing_time) values (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, post.getWriter());
            ps.setString(2, post.getTitle());
            ps.setString(3, post.getContents());
            ps.setTimestamp(4, java.sql.Timestamp.valueOf(post.getWritingTime()));
            return ps;
        }, keyHolder);

        long key = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return post.create(key);
    }

    @Override
    public Optional<Post> findById(long id) {
        String sql = "select id, writer, title, contents, writing_time from post where id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper(), id));
    }

    @Override
    public List<Post> findAll() {
        String sql = "select id, writer, title, contents, writing_time from post";
        return jdbcTemplate.query(sql, rowMapper());
    }

    private RowMapper<Post> rowMapper() {
        return (rs, rowNum) -> new Post(
                rs.getLong("id"),
                rs.getString("writer"),
                rs.getString("title"),
                rs.getString("contents"),
                rs.getTimestamp("writing_time").toLocalDateTime()
        );
    }
}
