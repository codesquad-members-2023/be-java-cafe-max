package kr.codesqaud.cafe.repository.reply;

import kr.codesqaud.cafe.domain.Reply;
import kr.codesqaud.cafe.exception.article.PrimaryKeyGenerationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Repository
public class MySqlReplyRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<Reply> replyRowMapper = BeanPropertyRowMapper.newInstance(Reply.class);

    public MySqlReplyRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long save(Reply reply) {
        final String sql = "INSERT INTO Reply (articleId, userId, contents, createdAt) VALUES (:articleId, :userId, :contents, :createdAt)";
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(reply), keyHolder);
        if (keyHolder.getKey() == null) {
            throw new PrimaryKeyGenerationException();
        }

        return keyHolder.getKey().longValue();
    }

    public Reply findByReplyId(Long replyId) {
        final String sql = "SELECT id, articleId, userId, contents, createdAt FROM Reply WHERE id = :id AND deleted = false LIMIT 1";
        try (final Stream<Reply> result = jdbcTemplate.queryForStream(sql, Map.of("id", replyId), replyRowMapper)) {
            return result.findFirst().orElseThrow();
        }
    }

    public List<Reply> findByArticleId(Long articleId) {
        final String sql = "SELECT id, userId, contents, createdAt FROM Reply WHERE articleId = :articleId AND deleted = false";
        return jdbcTemplate.query(sql, Map.of("articleId", articleId), replyRowMapper);
    }

    public int delete(Long replyId) {
        final String sql = "UPDATE Reply SET deleted = true WHERE id = :id";
        return jdbcTemplate.update(sql, Map.of("id", replyId));
    }
}
