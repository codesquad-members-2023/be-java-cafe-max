package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Reply;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcTemplateReplyRepository implements ReplyRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateReplyRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Long save(Reply reply) {

        String sql = "insert into reply (userId, writer, articleId, contents) values (?, ?, ?, ?)";

        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(reply);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, sqlParameterSource, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public List<Reply> findByArticleId(Long articleId) {
        String sql = "select * from reply where articleId = ? AND deleted = false";
        List<Reply> replies = jdbcTemplate.query(sql, replyRowMapper(), articleId);
        return replies;
    }

    @Override
    public Optional<Reply> findById(Long id) {
        String sql = "select * from reply where id = ? AND deleted = false";
        try {

            Reply reply = jdbcTemplate.queryForObject(sql, replyRowMapper(), id);
            return Optional.ofNullable(reply);

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void updateContents(Long id, String updateContents) {
        jdbcTemplate.update("update reply set contents = ? where id = ?", updateContents, id);
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("update reply set deleted = ? where id = ?", true, id);
    }

    private RowMapper<Reply> replyRowMapper() {
        return (rs, rowNum) -> {
            Reply reply = new Reply.ReplyBuilder(rs.getString("userId"), rs.getLong("articleId"))
                    .setWriter(rs.getString("writer"))
                    .setId(rs.getLong("id"))
                    .setContents(rs.getString("contents"))
                    .setCreatedTime(rs.getTimestamp("createdTime").toLocalDateTime())
                    .setDeleted(rs.getBoolean("deleted"))
                    .build();
            return reply;
        };
    }
}
