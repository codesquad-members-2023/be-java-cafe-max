package kr.codesqaud.cafe.repository.comment;

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

import javax.sql.DataSource;

import kr.codesqaud.cafe.domain.Comment;

@Repository
public class JdbcCommentRepository implements CommentRepository {
    private final RowMapper<Comment> commentRowMapper = (rs, rowNum) -> {
        Long commentId = rs.getLong("commentId");
        Long postId = rs.getLong("postId");
        Long memberId = rs.getLong("memberId");
        String writer = rs.getString("writer");
        String content = rs.getString("content");
        LocalDateTime updatedDate = null;
        if(rs.getTimestamp("update_date") != null){
            updatedDate = rs.getTimestamp("update_date").toLocalDateTime();
        }
        return new Comment(commentId, memberId, postId, writer, content, updatedDate);
    };
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcCommentRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Long save(Comment comment) {
        String sql = "INSERT INTO comment(postId,memberId,writer,content,create_date) VALUES(:postId,:memberId,:writer,:content,:createDate)";
        SqlParameterSource parameter = new BeanPropertySqlParameterSource(comment);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, parameter, keyHolder);
        Number key = Objects.requireNonNull(keyHolder.getKey());
        return key.longValue();
    }

    @Override
    public List<Comment> findComments(Long postId) {
        String sql = "SELECT commentId, postId, memberId,writer, content, create_date, update_date FROM comment WHERE postId = :postId";
        final MapSqlParameterSource parameter = new MapSqlParameterSource().addValue("postId", postId);
        return jdbcTemplate.query(sql, parameter, commentRowMapper);
    }


    @Override
    public void update(Comment comment) {
        String sql = "UPDATE comment SET content = :content WHERE writer = :writer";
        SqlParameterSource parameter = new BeanPropertySqlParameterSource(comment);
        jdbcTemplate.update(sql, parameter);
    }

    @Override
    public void deleteCommentId(Long commentId) {
        String sql = "DELETE FROM comment WHERE commentId = :commentId";
        SqlParameterSource parameter = new MapSqlParameterSource("commentId", commentId);
        jdbcTemplate.update(sql, parameter);
    }

    @Override
    public void deletePostId(Long postId) {
        String sql = "DELETE FROM comment WHERE postId = :postId";
        SqlParameterSource parameter = new MapSqlParameterSource("postId", postId);
        jdbcTemplate.update(sql, parameter);
    }
}
