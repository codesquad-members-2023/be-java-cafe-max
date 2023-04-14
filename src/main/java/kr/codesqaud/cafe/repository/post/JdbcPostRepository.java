package kr.codesqaud.cafe.repository.post;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import kr.codesqaud.cafe.domain.Member;
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
        String sql = "INSERT INTO post(title, content, writer_id, write_date, views) "
                   + "VALUES(:title, :content, :writer.id, :writeDate, :views)";
        SqlParameterSource parameter = new BeanPropertySqlParameterSource(post);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, parameter, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public Optional<Post> findById(Long id) {
        String sql = "SELECT p.id, p.title, p.content, m.id as writer_id, m.nickname as writer_name, "
                          + "p.write_date, p.views "
                     + "FROM post p "
               + "INNER JOIN member m on m.id = p.writer_id "
            + "        WHERE p.id = :id";
        SqlParameterSource parameter = new MapSqlParameterSource("id", id);
        return Optional.ofNullable(DataAccessUtils.singleResult(jdbcTemplate.query(sql, parameter, postRowMapper)));
    }

    @Override
    public List<Post> findAll() {
        String sql = "SELECT p.id, p.title, p.content, m.id as writer_id, m.nickname as writer_name,"
                         + " p.write_date, p.views "
                    + "FROM post p "
              + "INNER JOIN member m on m.id = p.writer_id "
                   + "ORDER BY id DESC";
        return jdbcTemplate.query(sql, postRowMapper);
    }

    @Override
    public void update(Post post) {
        String sql = "UPDATE post "
                      + "SET title = :title, "
                          + "content = :content "
                    + "WHERE id = :id";
        SqlParameterSource parameter = new BeanPropertySqlParameterSource(post);
        jdbcTemplate.update(sql, parameter);
    }

    @Override
    public void increaseViews(Post post) {
        String sql = "UPDATE post "
                      + "SET views = :views "
                    + "WHERE id = :id";
        SqlParameterSource parameter = new BeanPropertySqlParameterSource(post);
        jdbcTemplate.update(sql, parameter);
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM post "
                   + "WHERE id = :id";
        SqlParameterSource parameter = new MapSqlParameterSource("id", id);
        jdbcTemplate.update(sql, parameter);
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM post";
        jdbcTemplate.update(sql, (SqlParameterSource) null);
    }

    private final RowMapper<Post> postRowMapper = (rs, rowNum) ->
         new Post(rs.getLong("id"), rs.getString("title"),
            rs.getString("content"),
            Member.builder()
                .id(rs.getLong("writer_id"))
                .nickName(rs.getString("writer_name"))
                .build(),
            rs.getTimestamp("write_date").toLocalDateTime(),
            rs.getLong("views"));
}
