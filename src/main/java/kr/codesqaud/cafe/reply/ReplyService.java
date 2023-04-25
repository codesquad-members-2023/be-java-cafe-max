package kr.codesqaud.cafe.reply;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kr.codesqaud.cafe.global.mapper.ReplyMapper;
import kr.codesqaud.cafe.reply.domain.Reply;
import kr.codesqaud.cafe.reply.dto.LoadMoreReplyDto;
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
		return replyMapper.toReplyResponse(replyRepository.saveReply(reply));
	}

	public List<ReplyResponse> getRepliesByIdx(LoadMoreReplyDto articleIdx) {
		return replyRepository.findAllReply(articleIdx).stream()
			.map(replyMapper::toReplyResponse)
			.collect(Collectors.toUnmodifiableList());
	}

	public Result delete(String userId, Long replyIdx) {
		if (validSessionIdAndReplyId(userId, replyIdx)) {
			replyRepository.deleteReply(userId, replyIdx);
			return Result.ok();
		}
		return Result.fail("삭제불가");
	}

	public boolean validSessionIdAndReplyId(String userId, Long replyIdx) {
		return Objects.equals(userId, findIdByIdx(replyIdx));
	}

	public String findIdByIdx(Long replyIdx) {
		return replyRepository.findReplyIdByIdx(replyIdx);
	}

	public int getCountOfReplies(Long articleIdx) {
		return replyRepository.getCountOfReplies(articleIdx);
	}
}
