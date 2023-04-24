package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Reply;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcReplyRepositoryImpl implements ReplyRepository {

    private final NamedParameterJdbcTemplate template;

    public JdbcReplyRepositoryImpl(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Reply> findAll(Long articleId) {
        final String sql = "" +
                "SELECT id, article_id, user_id, writer, comments, created_at, updated_at " +
                "FROM reply " +
                "WHERE reply.article_id = :articleId";

        return template.query(sql, Map.of("articleId", articleId), replyRowMapper());
    }

    private RowMapper<Reply> replyRowMapper() {
        return (rs, rowNum) -> new Reply(
                rs.getLong("id"),
                rs.getLong("article_id"),
                rs.getLong("user_id"),
                rs.getString("writer"),
                rs.getString("comments"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
        );
    }
}
