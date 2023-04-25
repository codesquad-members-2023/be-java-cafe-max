package kr.codesqaud.cafe.reply.repository.impl;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.reply.domain.Reply;
import kr.codesqaud.cafe.reply.dto.LoadMoreReplyDto;
import kr.codesqaud.cafe.reply.repository.ReplyRepository;

@Repository
public class JDBCReplyRepository implements ReplyRepository {

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public JDBCReplyRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	public Reply saveReply(Reply reply) {
		namedParameterJdbcTemplate.update(
			"INSERT INTO REPLY (article_idx,user_id,content) VALUES (:article_idx, :userId, :content)"
			, new MapSqlParameterSource()
				.addValue("article_idx", reply.getArticleIdx())
				.addValue("userId", reply.getUserId())
				.addValue("content", reply.getContent()));

		return findReplyByReplyIdx();
	}

	private Reply findReplyByReplyIdx() {
		return namedParameterJdbcTemplate.queryForObject("SELECT A.nickName,B.* FROM USER A INNER JOIN REPLY B "
				+ "ON A.user_id = B.user_id WHERE reply_idx = LAST_INSERT_ID()",
			new MapSqlParameterSource(), (rs, rn) -> new Reply(rs));
	}

	@Override
	public List<Reply> findAllReply(LoadMoreReplyDto loadMoreReplyDto) {
		return namedParameterJdbcTemplate.query(
			"SELECT A.nickName, B.* FROM USER A INNER JOIN REPLY B ON A.user_id = B.user_id "
				+ "WHERE article_Idx = :articleIdx AND is_visible = true LIMIT :start,:count",
			new MapSqlParameterSource()
				.addValue("articleIdx", loadMoreReplyDto.getArticleIdx())
				.addValue("start", loadMoreReplyDto.getStart())
				.addValue("count", loadMoreReplyDto.getRecordSize()),
			(rs, rn) -> new Reply(rs));
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

	@Override
	public Long getCountOfReplies(Long articleIdx) {
		List<Long> countList = namedParameterJdbcTemplate.query(
			"SELECT COUNT(*) FROM REPLY WHERE is_visible = true AND article_idx = :articleIdx ",
			new MapSqlParameterSource("articleIdx", articleIdx),
			(rs, rowNum) -> rs.getLong(1));
		return countList.get(0);
	}
}
