package kr.codesqaud.cafe.comment.repository;

import kr.codesqaud.cafe.comment.domain.Comment;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Objects;

@Repository
public class CommentRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CommentRepository(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public long save(Comment comment) {
        String sql = "INSERT INTO comment (article_id, contents, user_id, created_time) VALUES (:articleId, :contents, :userId, :createdTime)";
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(comment);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, sqlParameterSource, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public Comment findById(long id) {
        String sql = "SELECT comment_id, article_id, contents, user_id, created_time FROM comment WHERE comment_id = :id";
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
        return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, commentRowMapper());
    }

    private RowMapper<Comment> commentRowMapper() {
        return (rs, rowNum) -> new Comment(
                rs.getLong("comment_id"),
                rs.getLong("article_id"),
                rs.getString("contents"),
                rs.getString("user_id"),
                rs.getTimestamp("created_time").toLocalDateTime()
        );
    }

    public List<Comment> findByArticleId(long id) {
        String sql = "SELECT comment_id, article_id, contents, user_id, created_time FROM comment WHERE article_id = :id";
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
        return namedParameterJdbcTemplate.query(sql, namedParameters, commentRowMapper());
    }

    public void deleteById(long commentId) {
        String sql = "DELETE FROM comment WHERE comment_id = :commentId";
        SqlParameterSource namedParameters = new MapSqlParameterSource("commentId", commentId);
        namedParameterJdbcTemplate.update(sql, namedParameters);
    }
}
