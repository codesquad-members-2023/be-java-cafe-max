package kr.codesqaud.cafe.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import kr.codesqaud.cafe.controller.dto.CommentRequest;
import kr.codesqaud.cafe.service.CommentService;

@Controller
public class CommentController {
	private final CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	@PostMapping("/comments")
	public String newComment(@ModelAttribute CommentRequest request, HttpSession session) {
		Object userId = session.getAttribute("sessionedUser");
		commentService.commentSave(request, (String)userId, request.getArticleId());
		return "redirect:/articles/" + request.getArticleId();
	}

	@DeleteMapping("/comments/{id}")
	public String deleteComment(@PathVariable Long id, HttpSession session, String userId, Long articleId) {
		Object user = session.getAttribute("sessionedUser");
		if (!user.equals(userId)) {
			return "user/error";
		}
		commentService.commentDelete(id);
		return "redirect:/articles/" + articleId;
	}
}
