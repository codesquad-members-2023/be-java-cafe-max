package kr.codesqaud.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import kr.codesqaud.cafe.controller.dto.CommentRequest;
import kr.codesqaud.cafe.service.CommentService;

@Controller
public class CommentController {
	private final CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	@PostMapping("/articles/{articleId}/comments")
	public String newComment(@ModelAttribute CommentRequest request, @SessionAttribute("sessionedUser") String userId) {
		commentService.save(request, userId, request.getArticleId());
		return "redirect:/articles/" + request.getArticleId();
	}

	@DeleteMapping("/articles/{articleId}/comments/{id}")
	public String deleteComment(@PathVariable Long id, @SessionAttribute("sessionedUser") String sessionUserId,
		String userId, Long articleId) {
		if (!sessionUserId.equals(userId)) {
			return "user/error";
		}
		commentService.delete(id);
		return "redirect:/articles/" + articleId;
	}
}
