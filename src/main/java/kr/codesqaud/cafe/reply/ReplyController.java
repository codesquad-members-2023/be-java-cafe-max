package kr.codesqaud.cafe.reply;

import static kr.codesqaud.cafe.global.config.Session.*;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import kr.codesqaud.cafe.global.config.Session;
import kr.codesqaud.cafe.reply.dto.LoadMoreReplyDto;
import kr.codesqaud.cafe.reply.dto.ReplyRequest;
import kr.codesqaud.cafe.reply.dto.ReplyResponse;
import kr.codesqaud.cafe.reply.dto.Result;

@RestController
public class ReplyController {

	private final ReplyService replyService;

	public ReplyController(ReplyService replyService) {
		this.replyService = replyService;
	}

	@PostMapping("/articles/reply/{articleIdx}")
	public ReplyResponse reply(@PathVariable Long articleIdx, @ModelAttribute @Valid ReplyRequest replyRequest,
		@SessionAttribute(LOGIN_USER) Session session) {
		replyRequest.init(session.getId(), articleIdx);
		return replyService.save(replyRequest);
	}

	@DeleteMapping("/articles/reply/{replyIdx}")
	public Result deleteReply(@PathVariable Long replyIdx, @SessionAttribute(LOGIN_USER) Session session) {
		return replyService.delete(session.getId(), replyIdx);
	}

	/**
	 * 더이상 load할 댓글이 없는데 불필요한 db접근을 막기 위해서 if문을 사용한다.
	 * @param loadMoreReplyDto
	 * @return
	 */
	@GetMapping("/articles/reply/loadMoreReply")
	public List<ReplyResponse> loadMoreReply(@ModelAttribute LoadMoreReplyDto loadMoreReplyDto) {
		Long countOfReplies = replyService.getCountOfReplies(loadMoreReplyDto.getArticleIdx());
		loadMoreReplyDto.setCountOfRepliesInDb(countOfReplies);
		if (loadMoreReplyDto.hasMoreRepliesToLoad()) {
			return replyService.getRepliesByIdx(loadMoreReplyDto);
		}
		return null;
	}

}
