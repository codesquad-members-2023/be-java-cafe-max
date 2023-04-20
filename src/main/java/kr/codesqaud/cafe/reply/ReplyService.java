package kr.codesqaud.cafe.reply;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kr.codesqaud.cafe.global.mapper.ReplyMapper;
import kr.codesqaud.cafe.reply.domain.Reply;
import kr.codesqaud.cafe.reply.dto.ReplyRequest;
import kr.codesqaud.cafe.reply.dto.ReplyResponse;
import kr.codesqaud.cafe.reply.dto.Result;
import kr.codesqaud.cafe.reply.repository.ReplyRepository;

@Service
public class ReplyService {

	private final ReplyRepository replyRepository;
	private final ReplyMapper replyMapper;

	public ReplyService(ReplyRepository replyRepository, ReplyMapper replyMapper) {
		this.replyRepository = replyRepository;
		this.replyMapper = replyMapper;
	}

	public ReplyResponse save(ReplyRequest replyRequest) {
		Reply reply = replyMapper.toReply(replyRequest);
		Long replyIdx = replyRepository.saveReply(reply);
		return replyMapper.toReplyResponse(reply, replyIdx);
	}

	public List<ReplyResponse> getRepliesByIdx(Long idx) {
		return replyRepository.findAllReply(idx).stream()
			.map(replyMapper::toReplyResponse)
			.collect(Collectors.toUnmodifiableList());
	}

	public Result delete(String id, Long replyIdx) {
		if (validSessionIdAndReplyId(id, replyIdx)) {
			replyRepository.deleteReply(id, replyIdx);
			return Result.ok();
		}
		return Result.fail("삭제불가");
	}

	public boolean validSessionIdAndReplyId(String id, Long replyIdx) {
		return Objects.equals(id, findIdByIdx(replyIdx));
	}

	public String findIdByIdx(Long replyIdx) {
		return replyRepository.findReplyIdByIdx(replyIdx);
	}
}
