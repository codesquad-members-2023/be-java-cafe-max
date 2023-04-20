package codesquad.cafe.domain.reply.repository;

import codesquad.cafe.domain.reply.domain.Reply;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ReplyRepositoryImpl implements ReplyRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ReplyRepositoryImpl(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void save(final Reply reply) {
        String sql = "INSERT INTO reply (contents, createdAt, user_id, article_id) VALUES( :contents, :createdAt, :user_id, :article_id)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("contents", reply.getContents());
        params.addValue("createdAt", reply.getCreatedAt());
        params.addValue("user_id", reply.getUserId());
        params.addValue("article_id", reply.getPostId());
        namedParameterJdbcTemplate.update(sql, params);
    }
}
