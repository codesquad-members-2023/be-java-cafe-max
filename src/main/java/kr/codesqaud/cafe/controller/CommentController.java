package kr.codesqaud.cafe.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.codesqaud.cafe.dto.CommentRequest;
import kr.codesqaud.cafe.dto.CommentResponse;
import kr.codesqaud.cafe.dto.UserRequest;
import kr.codesqaud.cafe.service.ArticleService;

@RestController
public class CommentController {
	private ArticleService articleService;

	public CommentController(ArticleService articleService) {
		this.articleService = articleService;
	}

	@GetMapping("/comments/{articleIndex}")
	public List<CommentResponse> show(@PathVariable Long articleIndex) {
		return articleService.findCommentsByArticleIndex(articleIndex);
	}

	@PostMapping("/comments/{author}")
	public List<CommentResponse> create(CommentRequest commentRequest) {
		return articleService.createComment(commentRequest);
	}

	@DeleteMapping("/comments/{articleIndex}/{commentIndex}")
	public List<CommentResponse> delete(@PathVariable Long articleIndex, @PathVariable Long commentIndex,
		HttpSession session) {
		String nickname = ((UserRequest)session.getAttribute("sessionUser")).getNickname();
		articleService.checkIsAuthor(nickname, articleIndex, commentIndex);
		articleService.deleteComment(articleIndex, commentIndex);
		return articleService.findCommentsByArticleIndex(articleIndex);
	}

	@GetMapping("/comments/size/{articleIndex}")
	public int commentsSize(@PathVariable Long articleIndex) {
		return articleService.getCommentsSize(articleIndex);
	}

	@GetMapping("/comments/{articleIndex}/{commentLastIndex}")
	public List<CommentResponse> showMoreComments(@PathVariable Long articleIndex,
		@PathVariable Long commentLastIndex) {
		return articleService.showMoreComments(articleIndex, commentLastIndex);
	}
}
