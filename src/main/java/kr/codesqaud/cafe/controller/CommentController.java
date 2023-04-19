package kr.codesqaud.cafe.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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
}
