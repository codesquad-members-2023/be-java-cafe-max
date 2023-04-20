package kr.codesqaud.cafe.reply.repository.impl;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.reply.domain.Reply;
import kr.codesqaud.cafe.reply.repository.ReplyRepository;

@Repository
public class JDBCReplyRepository implements ReplyRepository {

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public JDBCReplyRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	public Long saveReply(Reply reply) {
		namedParameterJdbcTemplate.update(
			"INSERT INTO REPLY (article_idx,user_id,nickName,content,date) VALUES (:article_idx, :userId, :nickName, :content, :date)"
			, new MapSqlParameterSource()
				.addValue("article_idx", reply.getArticleIdx())
				.addValue("userId", reply.getUserId())
				.addValue("nickName", reply.getNickName())
				.addValue("content", reply.getContent())
				.addValue("date", reply.getDate()));

		return namedParameterJdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()",
			new MapSqlParameterSource(), Long.class);
	}

	@Override
	public List<Reply> findAllReply(Long articleIdx) {
		return namedParameterJdbcTemplate.query(
			"SELECT nickName,content,date,article_idx,reply_idx FROM REPLY WHERE article_Idx = :articleIdx AND is_visible = true",
			new MapSqlParameterSource("articleIdx", articleIdx), (rs, rn) -> new Reply(rs));
	}

	@Override
	public void deleteReply(String userId, Long replyIdx) {
		namedParameterJdbcTemplate.update("UPDATE REPLY SET is_visible = false WHERE reply_idx = :replyIdx",
			new MapSqlParameterSource("replyIdx", replyIdx));
	}

	@Override
	public String findReplyIdByIdx(Long replyIdx) {
		return namedParameterJdbcTemplate.queryForObject(
			"SELECT user_id FROM REPLY WHERE reply_idx = :replyIdx", new MapSqlParameterSource("replyIdx", replyIdx),
			(rs, rowNum) -> rs.getString("user_id")
		);
	}
}
