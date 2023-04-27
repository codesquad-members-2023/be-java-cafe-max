package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Reply;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class JdbcReplyRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcReplyRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void save(Reply reply) {
        String sql = "insert into replies(articleId, writer, contents, writtenTime) values(?, ?, ?, current_timestamp)";
        jdbcTemplate.update(sql, reply.getArticleId(), reply.getWriter(), reply.getContents());
    }

    public List<Reply> findAllByArticleId(long articleId) {
        return jdbcTemplate.query("select * from replies where articleId = ?", replyRowMapper(), articleId);
    }

    private RowMapper<Reply> replyRowMapper() {
        return (rs, rowNum) -> {
            return Reply.builder()
                    .id(rs.getLong("id"))
                    .articleId(rs.getLong("articleId"))
                    .writer(rs.getString("writer"))
                    .contents(rs.getString("contents"))
                    .writtenTime(rs.getTimestamp("writtenTime").toLocalDateTime())
                    .build();
        };
    }
}
