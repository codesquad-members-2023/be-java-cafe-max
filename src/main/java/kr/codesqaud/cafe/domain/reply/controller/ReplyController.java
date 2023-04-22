package kr.codesqaud.cafe.domain.reply.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import kr.codesqaud.cafe.domain.reply.dto.request.ReplySaveRequestDto;
import kr.codesqaud.cafe.domain.reply.repository.ReplyRepository;
import kr.codesqaud.cafe.global.common.constant.SessionAttributeNames;

@Controller
public class ReplyController {

	private final ReplyRepository replyRepository;

	ReplyController(ReplyRepository replyRepository) {
		this.replyRepository = replyRepository;
	}

	@PostMapping("/reply/{articleId}")
	public String saveReply(@PathVariable("articleId") Long articleId, ReplySaveRequestDto replySaveRequestDto,
		HttpSession httpSession) {
		replyRepository.save(replySaveRequestDto.toEntity(articleId,
			(String)httpSession.getAttribute(SessionAttributeNames.LOGIN_USER_NAME.type())));
		return "redirect:/articles/{articleId}";
	}

	@DeleteMapping("/articles/{articleId}/replies/{replyId}")
	public String deleteReply(@PathVariable("articleId") Long articleId, @PathVariable("replyId") String replyId,
		HttpSession httpSession, Model model) {
		if (replyRepository.findById(replyId).get().getWriter() != httpSession.getAttribute(
			SessionAttributeNames.LOGIN_USER_NAME.type())) {
			model.addAttribute("error", "댓글작성자만  삭제할 수 있습니다.");
			return "redirect:/articles/{articleId}";
		}
		replyRepository.delete(replyId);
		return "redirect:/articles/{articleId}";
	}
}
