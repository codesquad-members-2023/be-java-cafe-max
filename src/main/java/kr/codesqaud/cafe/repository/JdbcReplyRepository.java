package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Reply;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcReplyRepository implements ReplyRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Reply> replyMapper = (rs, rowNum) -> new Reply(
            rs.getLong("article_id"),
            rs.getLong("reply_id"),
            rs.getString("user_id"),
            rs.getString("user_name"),
            rs.getString("comment"),
            rs.getTimestamp("created_at").toLocalDateTime()
    );

    public JdbcReplyRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reply save(Reply reply) {
        jdbcTemplate.update("insert into replies(article_id, user_id, user_name, comment, created_at) values(?, ?, ?, ?, ?)",
                reply.getArticleId(),
                reply.getUserId(),
                reply.getUserName(),
                reply.getComment(),
                reply.getCreatedAt()
        );
        return reply;
    }

    @Override
    public Optional<Reply> findByReplyId(Long replyId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject("SELECT * FROM replies WHERE reply_id = ?", replyMapper, replyId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Reply> findAllByArticleId(Long articleId) {
        return jdbcTemplate.query("SELECT * FROM replies where article_id = ?", replyMapper, articleId);
    }

    @Override
    public void delete(Long replyId) {
        jdbcTemplate.update("DELETE FROM replies WHERE reply_id = ?", replyId);
    }

    @Override
    public void update(Reply reply) {
        jdbcTemplate.update("UPDATE replies SET comment = ? WHERE reply_id = ?",
                reply.getComment(), reply.getReplyId());
    }
}
