package kr.codesqaud.cafe.board.repository;

import kr.codesqaud.cafe.board.domain.BoardPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class BoardJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BoardJdbcRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void save(BoardPost boardPost) {
        jdbcTemplate.update(
                "INSERT INTO post (writer, title, contents, writedatetime) VALUES (?, ?, ?, NOW())",
                boardPost.getWriter(), boardPost.getTitle(), boardPost.getContents());
    }

    public BoardPost findByPostId(Long postId) {
        return jdbcTemplate.queryForObject(
                "SELECT postid, writer, title, contents, writedatetime FROM post WHERE postid = ?",
                postRowMapper,
                postId);
    }

    public List<BoardPost> findAll() {
        return jdbcTemplate.query("SELECT * FROM post ORDER BY writedatetime DESC", postRowMapper);
    }

    private final RowMapper<BoardPost> postRowMapper = (resultSet, rowNum) -> {
        return new BoardPost(resultSet.getLong("postid"),
                resultSet.getString("writer"),
                resultSet.getString("title"),
                resultSet.getString("contents"),
                ((Timestamp) resultSet.getObject("writedatetime")).toLocalDateTime());
    };
}
