package codesquad.cafe.domain.reply.repository;

import codesquad.cafe.domain.reply.domain.Reply;
import codesquad.cafe.domain.user.domain.User;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Override
    public List<Reply> findAllByPost(final Long postId) {
        String sql = "SELECT * FROM reply WHERE article_id = :article_id";
        MapSqlParameterSource params = new MapSqlParameterSource("article_id", postId);
        return namedParameterJdbcTemplate.query(sql, params,
                (rs, rowNum) -> new Reply(
                        rs.getLong("id"),
                        rs.getString("contents"),
                        rs.getTimestamp("createdAt").toLocalDateTime(),
                        rs.getString("user_id"),
                        rs.getLong("article_id")));
    }

    @Override
    public String findUserNameByReply(final Reply reply) {
        String sql = "SELECT name FROM reply JOIN users ON users.id = reply.user_id WHERE reply.id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", reply.getId());
        return namedParameterJdbcTemplate.queryForObject(sql, params, String.class);
    }

    @Override
    public User findUserById(final Long replyId) {
        String sql = "SELECT * FROM users JOIN reply ON reply.user_id = users.id WHERE reply.id = :replyId";
        MapSqlParameterSource params = new MapSqlParameterSource("replyId", replyId);
        return namedParameterJdbcTemplate.queryForObject(sql, params,
                (rs, rowNum) -> new User(
                        rs.getString("id")
                        , rs.getString("password")
                        , rs.getString("name")
                        , rs.getString("email")));
    }

    @Override
    public void delete(final Long replyId) {
        String sql = "DELETE FROM reply WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", replyId);
        namedParameterJdbcTemplate.update(sql, params);
    }
}
