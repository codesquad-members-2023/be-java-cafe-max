package kr.codesqaud.cafe.repository.reply;

import kr.codesqaud.cafe.domain.Reply;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcTemplateReplyRepository implements ReplyRepository {
    private final NamedParameterJdbcTemplate template;
    private final RowMapper<Reply> replyRowMapper = BeanPropertyRowMapper.newInstance(Reply.class);

    public JdbcTemplateReplyRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Reply save(Reply reply) {
        String sql = "insert into REPLIES (article_id, user_id, reply_content, reply_time, deleted) " +
                "values (:articleId, :userId, :replyContent, :replyTime, false)";
        SqlParameterSource param = new BeanPropertySqlParameterSource(reply);
        template.update(sql, param);
        return reply;
    }

    @Override
    public Optional<Reply> findById(Long id) {
        String sql = "select id, article_id, user_id, reply_content, reply_time from REPLIES where id = :id and deleted=false";

        try {
            Map<String, Object> param = Map.of("id", id);
            Reply reply = template.queryForObject(sql, param, replyRowMapper);
            return Optional.ofNullable(reply);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Reply> findByArticleId(Long articleId) {
        // 1차: articleId -> 2차: userId
        String sql = "select r.id, r.user_id, u.user_id, r.reply_content, r.reply_time " +
                "from REPLIES r join USERS u on r.user_id = u.user_id " +
                "where r.article_id=:articleId and r.deleted=false";

        Map<String, Object> param = Map.of("articleId", articleId);
        return template.query(sql, param, replyRowMapper);
    }

    @Override
    public void deleteReply(Long id) {
        String articleDeletedSql = "update REPLIES set deleted=true where id=:id";

        Map<String, Object> param = Map.of("id", id);
        template.update(articleDeletedSql, param);
    }
}
