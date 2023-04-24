package kr.codesqaud.cafe.board.repository;

import kr.codesqaud.cafe.board.domain.BoardPost;
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
import java.util.Map;
import java.util.Optional;

@Repository
public class BoardJdbcRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public BoardJdbcRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public void save(BoardPost boardPost) {
        jdbcTemplate.update(
                "INSERT INTO post (writer, title, contents) VALUES (:writer, :title, :contents)",
                new BeanPropertySqlParameterSource(boardPost));
    }

    public void update(BoardPost boardPost) {
        jdbcTemplate.update(
                "UPDATE post SET title = :title, contents = :contents WHERE post_id = :postId",
                new BeanPropertySqlParameterSource(boardPost));
    }

    public void delete(Long postId) {
        jdbcTemplate.update("UPDATE post SET deleted = TRUE WHERE post_id = :postId", Collections.singletonMap("postId", postId));
    }

    public boolean containsPostId(Long postId) {
        Map<String, Long> namedParameters = Collections.singletonMap("post_id", postId);
        Optional<Integer> countOfPost = Optional.ofNullable(jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM post WHERE post_id = :post_id AND deleted = FALSE", namedParameters, Integer.class));
        return countOfPost.orElse(0) > 0;
    }

    public BoardPost findByPostId(Long postId) {
        Map<String, Long> namedParameters = Collections.singletonMap("postId", postId);
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT post_id, writer, title, contents, write_date_time " +
                            "FROM post " +
                            "WHERE post_id = :postId AND deleted = FALSE",
                    namedParameters, postRowMapper);
        } catch (DataRetrievalFailureException e) {
            throw new ResourceNotFoundException("요청한 데이터가 존재하지 않습니다.");
        }
    }

    public List<BoardPost> findAll() {
        return jdbcTemplate.query("SELECT post_id, writer, title, contents, write_date_time " +
                "FROM post " +
                "WHERE deleted = FALSE " +
                "ORDER BY write_date_time DESC", postRowMapper);
    }

    public RowMapper<BoardPost> postRowMapper = new RowMapper<BoardPost>() {
        @Override
        public BoardPost mapRow(ResultSet rs, int rowNum) throws SQLException {
            return BoardPost.builder()
                    .postId(rs.getLong("post_id"))
                    .writer(rs.getString("writer"))
                    .title(rs.getString("title"))
                    .contents(rs.getString("contents"))
                    .writeDateTime(rs.getTimestamp("write_date_time").toLocalDateTime())
                    .build();
        }
    };
}
