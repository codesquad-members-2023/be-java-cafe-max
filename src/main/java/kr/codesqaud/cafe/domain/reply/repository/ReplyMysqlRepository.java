package kr.codesqaud.cafe.domain.reply.repository;

import kr.codesqaud.cafe.domain.reply.Reply;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
    public List<Reply> findAll(int articleIdx, int start) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("articleIdx", articleIdx);
        namedParameters.addValue("start", start);
        return namedParameterJdbcTemplate.query(
                "SELECT IDX , ARTICLE_IDX , REPLY_WRITER , REPLY_CONTENTS , DATE " +
                        "FROM REPLY WHERE DELETED = FALSE" +
                        " AND ARTICLE_IDX = :articleIdx LIMIT :start , 5  ",
                namedParameters, rowMapper()
        );
    }

    @Override
    public int allCount(int articleIdx) {
        SqlParameterSource namedParameters = new MapSqlParameterSource("articleIdx", articleIdx);
        return namedParameterJdbcTemplate.query("SELECT COUNT(*) FROM REPLY " +
                        "WHERE DELETED = FALSE AND ARTICLE_IDX = :articleIdx",
                namedParameters, (rs, rowNum) -> rs.getInt(1)).get(0);
    }

    @Override
    public void delete(int index) {
        SqlParameterSource namedParameters = new MapSqlParameterSource("index", index);
        namedParameterJdbcTemplate.update(
                "UPDATE REPLY SET DELETED = TRUE WHERE IDX = :index", namedParameters
        );
    }

//    @Override
//    public Optional<Reply> findByIdx(int index) {
//        SqlParameterSource namedParameters = new MapSqlParameterSource("index", index);
//        List<Reply> replies = namedParameterJdbcTemplate.query(
//                "SELECT  ID  WHERE IDX = :index", namedParameters, rowMapper()
//        );
//        return replies.stream().findFirst();
//    }

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

    @Override
    public boolean exist(int index) {
        SqlParameterSource namedParameters = new MapSqlParameterSource("index", index);
        final String sql = "SELECT EXISTS(SELECT 1 FROM REPLY WHERE IDX = :index AND DELETED = 0 LIMIT 1)";
        final int count = namedParameterJdbcTemplate.queryForObject(sql,namedParameters,Integer.class);
        return count > 0;
    }
}

