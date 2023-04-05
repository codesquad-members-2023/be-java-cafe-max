package kr.codesqaud.cafe.board.repository;

import kr.codesqaud.cafe.board.domain.BoardPost;
import kr.codesqaud.cafe.board.dto.PostResponseForm;
import kr.codesqaud.cafe.board.dto.PostWriteForm;
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

    public void write(PostWriteForm postWriteForm) {
        jdbcTemplate.update(
                "INSERT INTO post (writer, title, contents, writedatetime) VALUES (?, ?, ?, NOW())",
                postWriteForm.getWriter(), postWriteForm.getTitle(), postWriteForm.getContents());
    }

    public PostResponseForm getPost(Long postId) {
        return jdbcTemplate.queryForObject(
                "SELECT postid, writer, title, contents, writedatetime FROM post WHERE postid = ?",
                postRowMapper,
                postId);
    }

    public List<PostResponseForm> getPostList() {
        return jdbcTemplate.query("SELECT * FROM post", postRowMapper);
    }

    private final RowMapper<PostResponseForm> postRowMapper = (resultSet, rowNum) -> {
        return new BoardPost(resultSet.getLong("postid"),
                resultSet.getString("writer"),
                resultSet.getString("title"),
                resultSet.getString("contents"),
                ((Timestamp) resultSet.getObject("writedatetime")).toLocalDateTime()
        ).toPostResponseForm();
    };
}
