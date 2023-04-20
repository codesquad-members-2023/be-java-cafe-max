package kr.codesqaud.cafe.repository.comment;

import java.util.List;
import java.util.Objects;
import kr.codesqaud.cafe.domain.Comment;
import kr.codesqaud.cafe.domain.Member;
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
    public Long save(Comment comment) {
        String sql = "INSERT INTO comment(post_id, writer_id, content, write_date, is_deleted) "
                   + "VALUES(:postId, :writer.id, :content, :writerDate, :isDeleted)";
        SqlParameterSource param = new BeanPropertySqlParameterSource(comment);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, param, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public void delete(Long id) {
        String sql = "UPDATE comment SET is_deleted = TRUE "
                    + "WHERE id = :id";
        SqlParameterSource param = new MapSqlParameterSource("id", id);
        jdbcTemplate.update(sql, param);
    }

    @Override
    public List<Comment> findAllByPostId(Long postId) {
        String sql = "SELECT c.id, c.post_id, c.writer_id, m.nickname, c.content, c.write_date "
                     + "FROM comment c "
               + "INNER JOIN member m ON m.id = c.writer_id "
                    + "WHERE c.post_id = :postId "
                      + "AND is_deleted = false ";
        SqlParameterSource param = new MapSqlParameterSource("postId", postId);
        return jdbcTemplate.query(sql, param, commentRowMapper);
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
