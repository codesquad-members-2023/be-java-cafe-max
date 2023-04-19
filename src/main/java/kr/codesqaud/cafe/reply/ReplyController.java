package kr.codesqaud.cafe.reply;

import static kr.codesqaud.cafe.global.config.Session.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.codesqaud.cafe.global.config.Session;
import kr.codesqaud.cafe.reply.dto.ReplyRequest;
import kr.codesqaud.cafe.reply.dto.ReplyResponse;

@Controller
public class ReplyController {

	private final ReplyService replyService;

	public ReplyController(ReplyService replyService) {
		this.replyService = replyService;
	}

	@PostMapping("/articles/{articleIdx}/reply")
	@ResponseBody
	public ReplyResponse reply(@PathVariable Long articleIdx, @ModelAttribute @Valid ReplyRequest replyRequest,
		HttpSession httpSession) {
		Session session = getLoginUser(httpSession);
		replyRequest.init(session.getId(), articleIdx);
		return replyService.save(replyRequest);
	}

	@DeleteMapping("/articles/{articleIdx}/{replyIdx}")
	public String deleteReply(@PathVariable Long replyIdx, @PathVariable Long articleIdx, HttpSession httpSession) {
		Session session = getLoginUser(httpSession);
		replyService.delete(session.getId(), replyIdx);
		return "redirect:/articles/" + articleIdx;
	}
}
