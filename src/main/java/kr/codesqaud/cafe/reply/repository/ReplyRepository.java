package kr.codesqaud.cafe.reply.repository;

import java.util.List;

import kr.codesqaud.cafe.reply.domain.Reply;
import kr.codesqaud.cafe.reply.dto.LoadMoreReplyDto;

public interface ReplyRepository {

	Reply saveReply(Reply reply);

	List<Reply> findAllReply(LoadMoreReplyDto loadMoreReplyDto);

	void deleteReply(String userId, Long replyIdx);

	String findReplyIdByIdx(Long replyIdx);

	Integer getCountOfReplies(Long articleIdx);
}
