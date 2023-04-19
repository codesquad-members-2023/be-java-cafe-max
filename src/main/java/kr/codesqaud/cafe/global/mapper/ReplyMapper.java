package kr.codesqaud.cafe.global.mapper;

import org.springframework.stereotype.Component;

import kr.codesqaud.cafe.reply.domain.Reply;
import kr.codesqaud.cafe.reply.dto.ReplyRequest;
import kr.codesqaud.cafe.reply.dto.ReplyResponse;

@Component
public class ReplyMapper {

	public Reply toReply(ReplyRequest replyRequest) {
		return new Reply(replyRequest.getId(), replyRequest.getArticleIdx(), replyRequest.getNickName(),
			replyRequest.getContent());
	}

	public ReplyResponse toReplyResponse(Reply reply) {
		return new ReplyResponse(reply.getNickName(), reply.getContent(), reply.getDate(), reply.getArticleIdx(),
			reply.getReplyIdx());
	}
}
