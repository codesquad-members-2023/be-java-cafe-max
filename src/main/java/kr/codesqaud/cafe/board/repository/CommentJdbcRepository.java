package kr.codesqaud.cafe.board.repository;

import kr.codesqaud.cafe.board.domain.Comment;
import kr.codesqaud.cafe.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentJdbcRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public CommentJdbcRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public void save(Comment comment) {
        jdbcTemplate.update(
                "INSERT INTO comment (post_id, writer, contents) VALUES (:postId, :writer, :contents)",
                new BeanPropertySqlParameterSource(comment));
    }

    public void delete(Long commentId) {
        jdbcTemplate.update("UPDATE comment SET deleted = TRUE WHERE comment_id = :commentId", Collections.singletonMap("commentId", commentId));
    }

    public void deleteAllByPostId(Long postId) {
        jdbcTemplate.update("UPDATE comment SET deleted = TRUE WHERE post_id = :postId", Collections.singletonMap("postId", postId));
    }

    public Comment findByCommentId(Long commentId) {
        try {
            return jdbcTemplate.queryForObject("SELECT comment_id, post_id, writer, contents, write_date_time " +
                            "FROM comment " +
                            "WHERE comment_id = :commentId",
                    Collections.singletonMap("commentId", commentId), commentRowMapper);
        } catch (DataRetrievalFailureException e) {
            throw new ResourceNotFoundException("요청한 데이터가 존재하지 않습니다.");
        }
    }

    public List<Comment> findAllByPostId(Long postId) {
        return jdbcTemplate.query("SELECT comment_id, post_id, writer, contents, write_date_time " +
                        "FROM comment " +
                        "WHERE post_id = :postId AND deleted = FALSE " +
                        "ORDER BY write_date_time",
                Collections.singletonMap("postId", postId), commentRowMapper);
    }

    public RowMapper<Comment> commentRowMapper = new RowMapper<Comment>() {
        @Override
        public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Comment.builder()
                    .commentId(rs.getLong("comment_id"))
                    .postId(rs.getLong("post_id"))
                    .writer(rs.getString("writer"))
                    .contents(rs.getString("contents"))
                    .writeDateTime(rs.getTimestamp("write_date_time").toLocalDateTime())
                    .build();
        }
    };

    public int getCommentCountByOtherWriter(Long postId) {
        Optional<Integer> countOfPost = Optional.ofNullable(jdbcTemplate.queryForObject(
                "SELECT COUNT(*)\n" +
                        "FROM comment a\n" +
                        "JOIN post b ON a.post_id = b.post_id AND a.writer != b.writer\n" +
                        "WHERE a.post_id = :postId\n" +
                        "AND a.deleted = FALSE",
                Collections.singletonMap("postId", postId), Integer.class));
        return countOfPost.orElse(0);
    }
}
