package kr.codesqaud.cafe.reply;

import java.util.List;
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
        String sql = "insert into reply (article_id, users_id, contents) values (:articleId, :userId, :contents)";
        SqlParameterSource param = new BeanPropertySqlParameterSource(reply);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, param, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public Reply findById(long id) {
        return null;
    }

    /**
     * @param id 게시글 id
     */
    @Override
    public List<Reply> findAllByArticleId(long id) {
        String sql = "SELECT r.id, r.article_id, u.name as users_id, r.contents, r.create_dateTime "
                + "FROM reply r "
                + "INNER JOIN users u ON r.users_id = u.userId "
                + "WHERE article_id = :id";
        SqlParameterSource param = new MapSqlParameterSource("id", id);
        return template.query(sql, param, replyResponseDtoRowMapper());
    }

    private RowMapper<Reply> replyResponseDtoRowMapper() {
        return (resultSet, rowNumber) -> new Reply.Builder()
                .id(resultSet.getLong("id"))
                .articleId(resultSet.getLong("article_id"))
                .userId(resultSet.getString("users_id"))
                .contents(resultSet.getString("contents"))
                .createDateTime(resultSet.getString("create_dateTime"))
                .build();
    }

}
