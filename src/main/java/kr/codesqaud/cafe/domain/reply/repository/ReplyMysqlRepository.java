package kr.codesqaud.cafe.domain.reply.repository;

import kr.codesqaud.cafe.domain.reply.Reply;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReplyMysqlRepository implements ReplyRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ReplyMysqlRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void save(Reply reply) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(reply);
        namedParameterJdbcTemplate.update(
                "INSERT INTO REPLY(ARTICLE_IDX , REPLY_WRITER , REPLY_CONTENTS)" +
                        "VALUES (:articleIdx, :replyWriter, :replyContents)", params
        );

    }

    @Override
    public List<Reply> findAll(int articleIdx) {
        SqlParameterSource namedParameters = new MapSqlParameterSource("articleIdx", articleIdx);
        return namedParameterJdbcTemplate.query(
                "SELECT IDX , ARTICLE_IDX , REPLY_WRITER , REPLY_CONTENTS , DATE FROM REPLY WHERE DELETED = FALSE AND ARTICLE_IDX = :articleIdx ",
                namedParameters, rowMapper()
        );
    }

    private RowMapper<Reply> rowMapper() {
        return (rs, rowNum) ->
                new Reply.Builder()
                        .index(rs.getInt("IDX"))
                        .articleIdx(rs.getInt("ARTICLE_IDX"))
                        .replyWriter(rs.getString("REPLY_WRITER"))
                        .replyContents(rs.getString("REPLY_CONTENTS"))
                        .date(rs.getString("DATE"))
                        .build();
    }
}

