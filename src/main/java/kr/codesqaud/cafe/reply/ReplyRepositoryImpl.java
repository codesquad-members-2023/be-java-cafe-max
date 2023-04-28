package kr.codesqaud.cafe.reply;

import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ReplyRepositoryImpl implements ReplyRepository {

    private final NamedParameterJdbcTemplate template;

    public ReplyRepositoryImpl(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    @Override
    public long save(Reply reply) {
        String sql = "insert into reply (article_id, user_login_id, contents) values (:articleId, :loginId, :contents)";
        SqlParameterSource param = new BeanPropertySqlParameterSource(reply);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, param, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public void updateHasReply(long articleId, boolean hasReply) {
        String sql = "UPDATE article SET has_reply = :hasReply WHERE id = :articleId";
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("hasReply", hasReply)
                .addValue("articleId", articleId);
        template.update(sql, param);
    }

    // TODO: 댓글 수정 기능 구현 필요
//    @Override
//    public long update(Reply reply) {
//        String sql = "UPDATE reply "
//                + "SET contents = :contents "
//                + "WHERE id = :id";
//        SqlParameterSource param = new MapSqlParameterSource()
//                .addValue("id", reply.getId())
//                .addValue("contents", reply.getContents());
//        template.update(sql, param);
//        return reply.getId();
//    }

    /**
     * @param id 댓글 Id
     */
    @Override
    public long deleteOneByReplyId(long id) {
        String sql = "DELETE FROM reply WHERE id = :id";
        SqlParameterSource param = new MapSqlParameterSource("id", id);
        template.update(sql, param);
        return id;
    }

    @Override
    public Reply findById(long id) {
        String sql = "SELECT id, article_id, user_login_id, contents, create_dateTime"
                + " FROM reply"
                + " WHERE id = :id";
        SqlParameterSource param = new MapSqlParameterSource("id", id);
        return template.queryForObject(sql, param, replyRowMapper());
    }

    /**
     * @param id 게시글 id
     */
    @Override
    public List<Reply> findAllByArticleId(long id) {
        String sql = "SELECT r.id, r.article_id, u.name as user_login_id, r.contents, r.create_dateTime "
                + "FROM reply r "
                + "INNER JOIN user u ON r.user_login_id = u.login_Id "
                + "WHERE article_id = :id";
        SqlParameterSource param = new MapSqlParameterSource("id", id);
        return template.query(sql, param, replyRowMapper());
    }

    @Override // TODO: 댓글 개수는 Reply Or Article 어떤 레포지토리에서 가져와야 할지..?
    public Long getReplyCountOf(long articleId) {
        String sql = "SELECT COUNT(id) as reply_count FROM reply WHERE article_id = :articleId;";
        SqlParameterSource param = new MapSqlParameterSource("articleId", articleId);
        return template.queryForObject(sql, param, Long.class);
    }

    private RowMapper<Reply> replyRowMapper() {
        return (resultSet, rowNumber) -> new Reply.Builder()
                .id(resultSet.getLong("id"))
                .articleId(resultSet.getLong("article_id"))
                .userId(resultSet.getString("user_login_id"))
                .contents(resultSet.getString("contents"))
                .createDateTime(resultSet.getString("create_dateTime"))
                .build();
    }
}
