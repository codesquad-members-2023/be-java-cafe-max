package kr.codesqaud.cafe.repository.reply;

import kr.codesqaud.cafe.domain.Reply;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class JdbcReplyRepository implements ReplyRepository{
    private final JdbcTemplate jdbcTemplate;
    public JdbcReplyRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reply save(Reply reply) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("reply").usingGeneratedKeyColumns("reply_id");

        Map<String, Object> parameters = new ConcurrentHashMap<>();
        parameters.put("writer", reply.getWriter());
        parameters.put("contents", reply.getContents());
        parameters.put("created_at", reply.getCreatedAt());
        parameters.put("article_id", reply.getArticleId());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        reply.setReplyId(key.longValue());
        return reply;
    }

    private RowMapper<Reply> replyRowMapper(){
        return (rs, rowNum) -> {
            Reply reply = new Reply();
            reply.setReplyId(rs.getLong("reply_id"));
            reply.setWriter(rs.getString("writer"));
            reply.setContents(rs.getString("contents"));
            reply.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            if (rs.getTimestamp("modified_at") != null) {
                reply.setModifiedAt(rs.getTimestamp("modified_at").toLocalDateTime());
            }
            reply.setArticleId(rs.getLong("article_id"));
            return reply;
        };
    }

    @Override
    public Optional<Reply> findByReplyId(Long replyId) {
        List<Reply> result = jdbcTemplate.query("select * from reply where id = ?", replyRowMapper(), replyId);
        return result.stream().findAny();
    }

    @Override
    public List<Reply> findByArticleId(Long articleId) {
        return jdbcTemplate.query("select * from reply where article_id = ?", replyRowMapper(), articleId);
    }

    @Override
    public Optional<Reply> update(Long replyId, String contents) {
        jdbcTemplate.update("update reply set contents = ?, modified_at = ? where id = ?", contents, LocalDateTime.now(), replyId);
        return findByReplyId(replyId);
    }

    @Override
    public Long delete(Long replyId) {
        jdbcTemplate.update("delete from article where id=?", replyId);
        return replyId;
    }
}
