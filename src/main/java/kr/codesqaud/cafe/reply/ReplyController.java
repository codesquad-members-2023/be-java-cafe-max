package kr.codesqaud.cafe.reply;

import static kr.codesqaud.cafe.global.config.Session.*;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import kr.codesqaud.cafe.global.config.Session;
import kr.codesqaud.cafe.reply.dto.LoadMoreReplyDto;
import kr.codesqaud.cafe.reply.dto.ReplyRequest;
import kr.codesqaud.cafe.reply.dto.ReplyResponse;
import kr.codesqaud.cafe.reply.dto.Result;

@Controller
public class ReplyController {

	private final ReplyService replyService;

	public ReplyController(ReplyService replyService) {
		this.replyService = replyService;
	}

	@PostMapping("/articles/reply/{articleIdx}")
	@ResponseBody
	public ReplyResponse reply(@PathVariable Long articleIdx, @ModelAttribute @Valid ReplyRequest replyRequest,
		@SessionAttribute(LOGIN_USER) Session session) {
		replyRequest.init(session.getId(), articleIdx);
		return replyService.save(replyRequest);
	}

	@DeleteMapping("/articles/reply/{replyIdx}")
	@ResponseBody
	public Result deleteReply(@PathVariable Long replyIdx, @SessionAttribute(LOGIN_USER) Session session) {
		return replyService.delete(session.getId(), replyIdx);
	}

	@ResponseBody
	@GetMapping("/articles/reply/loadMoreReply")
	public List<ReplyResponse> loadMoreReply(@ModelAttribute LoadMoreReplyDto loadMoreReplyDto) {
		loadMoreReplyDto.setCountOfRepliesInDb(replyService.getCountOfReplies(loadMoreReplyDto.getArticleIdx()));
		return replyService.getRepliesByIdx(loadMoreReplyDto);
	}

}
