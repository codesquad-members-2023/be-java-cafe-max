package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.controller.dto.Pageable;
import kr.codesqaud.cafe.domain.Reply;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Objects;

@Repository
public class JdbcReplyRepositoryImpl implements ReplyRepository {

    private final NamedParameterJdbcTemplate template;

    public JdbcReplyRepositoryImpl(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Long save(Reply reply) {
        final String sql = "" +
                "INSERT INTO reply (article_id, user_id, writer, comments) " +
                "VALUES (:articleId, :userId, :writer, :comments)";

        final SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(reply);
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(sql, parameterSource, keyHolder);

        return (Long) Objects.requireNonNull(keyHolder.getKeys()).get("id");
    }

    @Override
    public List<Reply> findReplies(Long articleId, Pageable pageable) {
        final String sql = "" +
                "SELECT id, article_id, user_id, writer, comments, created_at, updated_at " +
                "FROM reply " +
                "WHERE reply.article_id = :articleId AND id >= :startId " +
                "LIMIT :size";

        final MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("articleId", articleId)
                .addValue("startId", pageable.getStartId())
                .addValue("size", pageable.getSize() + 1);

        return template.query(sql, params, replyRowMapper());
    }

    @Override
    public boolean update(Reply reply) {
        final String sql = "" +
                "UPDATE reply " +
                "SET comments = :comments " +
                "WHERE id = :id AND user_id = :userId";

        final MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("comments", reply.getComments())
                .addValue("id", reply.getId())
                .addValue("userId", reply.getUserId());

        return template.update(sql, params) > 0;
    }

    @Override
    public boolean delete(Long replyId, Long userId) {
        final String sql = "" +
                "DELETE FROM reply " +
                "WHERE id = :id AND user_id = :userId";

        final MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", replyId)
                .addValue("userId", userId);

        return template.update(sql, params) > 0;
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
