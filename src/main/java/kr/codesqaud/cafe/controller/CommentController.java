package kr.codesqaud.cafe.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.codesqaud.cafe.dto.CommentRequest;
import kr.codesqaud.cafe.dto.CommentResponse;
import kr.codesqaud.cafe.dto.CommentUpdateRequest;
import kr.codesqaud.cafe.dto.UserRequest;
import kr.codesqaud.cafe.service.ArticleService;

@RestController
public class CommentController {
	private ArticleService articleService;

	public CommentController(ArticleService articleService) {
		this.articleService = articleService;
	}

	@GetMapping("/comments/{articleIndex}/{commentLastIndex}")
	public List<CommentResponse> show(@PathVariable Long articleIndex,
		@PathVariable Long commentLastIndex) {
		return articleService.showComments(articleIndex, commentLastIndex);
	}

	@PostMapping("/comments/{author}")
	public boolean create(CommentRequest commentRequest) {
		articleService.createComment(commentRequest);
		return true;
	}

	@DeleteMapping("/comments/{commentIndex}")
	public boolean delete(@PathVariable Long commentIndex, HttpSession session) {
		String nickname = ((UserRequest)session.getAttribute("sessionUser")).getNickname();
		articleService.checkIsAuthor(nickname, commentIndex);
		articleService.deleteComment(commentIndex);
		return true;
	}

	@GetMapping("/comments/size/{articleIndex}")
	public int commentsSize(@PathVariable Long articleIndex) {
		return articleService.getCommentsSize(articleIndex);
	}

	@PatchMapping("/comments/{commentIndex}")
	public boolean update(@PathVariable Long commentIndex, CommentUpdateRequest commentUpdateRequest,
		HttpSession session) {
		String nickname = ((UserRequest)session.getAttribute("sessionUser")).getNickname();
		articleService.checkIsAuthor(nickname, commentIndex);
		articleService.updateComment(commentIndex, commentUpdateRequest);
		return true;
	}
}
