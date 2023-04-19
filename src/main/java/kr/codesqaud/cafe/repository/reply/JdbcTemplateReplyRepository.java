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
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper<Reply> replyRowMapper = BeanPropertyRowMapper.newInstance(Reply.class);

    public JdbcTemplateReplyRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("REPLIES")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Reply save(Reply reply) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(reply);
        Number key = simpleJdbcInsert.executeAndReturnKey(param);
        reply.setId(key.longValue());
        return reply;
    }

    @Override
    public Optional<Reply> findById(Long id) {
        String sql = "select id, article_id, user_id, reply_content, reply_time from REPLIES where id = :id";

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
                "where r.article_id=:articleId";

            Map<String, Object> param = Map.of("articleId", articleId);
            return template.query(sql, param, replyRowMapper);
        }
}
