package kr.codesqaud.cafe.repository.post;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import kr.codesqaud.cafe.domain.Post;
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
public class JdbcPostRepository implements PostRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcPostRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(Post post) {
        String sql = "INSERT INTO post(title, content, writer_id, write_date, views) VALUES(:title, :content, :writerId, :writeDate, :views)";
        SqlParameterSource parameter = new BeanPropertySqlParameterSource(post);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, parameter, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public Optional<Post> findById(Long id) {
        String sql = "SELECT id, title, content, writer_id, write_date, views FROM post WHERE id = :id";
        SqlParameterSource parameter = new MapSqlParameterSource("id", id);
        return Optional.ofNullable(DataAccessUtils.singleResult(jdbcTemplate.query(sql, parameter, postRowMapper)));
    }

    @Override
    public List<Post> findAll() {
        String sql = "SELECT id, title, content, writer_id, write_date, views FROM post";
        return jdbcTemplate.query(sql, postRowMapper);
    }

    @Override
    public void update(Post post) {
        String sql = "UPDATE post SET title = :title, content = :content, views = :views WHERE id = :id";
        SqlParameterSource parameter = new BeanPropertySqlParameterSource(post);
        jdbcTemplate.update(sql, parameter);
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM post";
        jdbcTemplate.update(sql, (SqlParameterSource) null);
    }

    private final RowMapper<Post> postRowMapper = (rs, rowNum) -> {
        long writerId = rs.getLong("writer_id");
        return new Post(rs.getLong("id"), rs.getString("title"),
            rs.getString("content"), writerId == 0 ? null : writerId,
            rs.getTimestamp("write_date").toLocalDateTime(), rs.getLong("views"));
    };
}
