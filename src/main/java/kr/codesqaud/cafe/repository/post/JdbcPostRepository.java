package kr.codesqaud.cafe.repository.post;

import java.util.List;
import java.util.Map;
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
        String sql = "INSERT INTO post(title, content, writer_id, write_date, views, is_deleted) "
            + "VALUES(:title, :content, :writer.id, :writeDateTime, :views, false)";
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
                    + "WHERE p.id = :id "
                    + "AND p.is_deleted = false ";
        SqlParameterSource parameter = new MapSqlParameterSource("id", id);
        return Optional.ofNullable(
            DataAccessUtils.singleResult(jdbcTemplate.query(sql, parameter, postRowMapper)));
    }

    @Override
    public List<Post> findAll(Integer startPage, Integer pageSize) {
        String sql = "SELECT p.id, p.title, p.content, m.id as writer_id, m.nickname as writer_name,"
                         + " p.write_date, p.views "
                    + "FROM post p "
                    + "INNER JOIN member m on m.id = p.writer_id "
                    + "WHERE p.is_deleted = false "
                    + "ORDER BY id DESC "
                    + "LIMIT :startPage, :pageSize";
        MapSqlParameterSource param = new MapSqlParameterSource("startPage", startPage);
        param.addValue("pageSize", pageSize);
        return jdbcTemplate.query(sql, param, postRowMapper);
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
    public void increaseViews(Long id) {
        String sql = "UPDATE post "
                      + "SET views = views + 1 "
                    + "WHERE id = :id";
        SqlParameterSource parameter = new MapSqlParameterSource("id", id);
        jdbcTemplate.update(sql, parameter);
    }

    @Override
    public void delete(Long id) {
        String sql = "UPDATE post "
                      + "SET is_deleted = true "
                    + "WHERE id = :id";
        SqlParameterSource parameter = new MapSqlParameterSource("id", id);
        jdbcTemplate.update(sql, parameter);
    }

    @Override
    public int postsSize() {
        String sql = "SELECT count(id) FROM post WHERE is_deleted = false";
        return jdbcTemplate.queryForObject(sql, Map.of(), Integer.class);
    }

    private final RowMapper<Post> postRowMapper = (rs, rowNum) ->
        new Post(rs.getLong("id"), rs.getString("title"),
            rs.getString("content"),
            Member.of(rs.getLong("writer_id"), rs.getString("writer_name")),
            rs.getTimestamp("write_date").toLocalDateTime(), rs.getLong("views"));

}
