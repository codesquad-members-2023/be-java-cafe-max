package kr.codesqaud.cafe.repository.comment;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import kr.codesqaud.cafe.domain.Comment;
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
public class JdbcCommentRepository implements CommentRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcCommentRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Comment save(Comment comment) {
        String sql = "INSERT INTO comment(post_id, writer_id, content, write_date, is_deleted) "
                   + "VALUES(:postId, :writer.id, :content, :writeDateTime, false)";
        SqlParameterSource param = new BeanPropertySqlParameterSource(comment);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, param, keyHolder);
        comment.injectionAutoIncrement(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return comment;
    }

    @Override
    public Optional<Comment> findById(Long id) {
        String sql = "SELECT c.id, c.post_id, c.writer_id, m.nickname, c.content, c.write_date "
                    + "FROM comment c "
                    + "INNER JOIN member m ON m.id = c.writer_id "
                    + "WHERE c.id = :id "
                      + "AND c.is_deleted = false ";
        SqlParameterSource param = new MapSqlParameterSource("id", id);
        return Optional.ofNullable(DataAccessUtils.singleResult(jdbcTemplate.query(sql, param, commentRowMapper)));
    }

    @Override
    public List<Comment> findAllByPostId(Long postId) {
        String sql = "SELECT c.id, c.post_id, c.writer_id, m.nickname, c.content, c.write_date "
                    + "FROM comment c "
                    + "INNER JOIN member m ON m.id = c.writer_id "
                    + "WHERE c.post_id = :postId "
                      + "AND c.is_deleted = false ";
        SqlParameterSource param = new MapSqlParameterSource("postId", postId);
        return jdbcTemplate.query(sql, param, commentRowMapper);
    }

    @Override
    public int delete(Long id) {
        String sql = "UPDATE comment "
                      + "SET is_deleted = true "
                    + "WHERE id = :id";
        SqlParameterSource param = new MapSqlParameterSource("id", id);
        return jdbcTemplate.update(sql, param);
    }

    @Override
    public void deleteAllByPostId(Long postId) {
        String sql = "UPDATE comment "
                      + "SET is_deleted = true "
                    + "WHERE post_id = :postId";
        SqlParameterSource param = new MapSqlParameterSource("postId", postId);
        jdbcTemplate.update(sql, param);
    }

    private final RowMapper<Comment> commentRowMapper = (rs, rowNum) ->
        Comment.builder()
            .id(rs.getLong("id"))
            .postId(rs.getLong("post_id"))
            .writer(Member.builder()
                .id(rs.getLong("writer_id"))
                .nickname(rs.getString("nickname"))
                .build())
            .content(rs.getString("content"))
            .writeDate(rs.getTimestamp("write_date").toLocalDateTime())
            .build();
}
