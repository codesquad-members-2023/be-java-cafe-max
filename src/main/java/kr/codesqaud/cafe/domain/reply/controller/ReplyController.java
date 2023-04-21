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

	@PostMapping("/reply/{id}")
	public String saveReply(@PathVariable("id") Long id, ReplySaveRequestDto replySaveRequestDto,
		HttpSession httpSession) {
		replyRepository.save(replySaveRequestDto.toEntity(id,
			(String)httpSession.getAttribute(SessionAttributeNames.LOGIN_USER_NAME.type())));
		return "redirect:/articles/{id}";
	}

	@DeleteMapping("/articles/{id}/replies/{replyId}")
	public String deleteReply(@PathVariable("id") Long id, @PathVariable("replyId") String replyId,
		HttpSession httpSession, Model model) {
		if (replyRepository.findById(replyId).get().getWriter() != httpSession.getAttribute(
			SessionAttributeNames.LOGIN_USER_NAME.type())) {
			model.addAttribute("error", "댓글작성자만  삭제할 수 있습니다.");
			return "redirect:/articles/{id}";
		}
		replyRepository.delete(replyId);
		return "redirect:/articles/{id}";
	}
}
