package kr.codesqaud.cafe.reply.repository;

import java.util.List;

import kr.codesqaud.cafe.reply.domain.Reply;

public interface ReplyRepository {

	Reply saveReply(Reply reply);

	List<Reply> findAllReply(Long idx);

	void deleteReply(String userId, Long replyIdx);

	String findReplyIdByIdx(Long replyIdx);
}
