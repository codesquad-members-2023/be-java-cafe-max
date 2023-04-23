package kr.codesqaud.cafe.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.codesqaud.cafe.domain.Comment;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.CommentDto;
import kr.codesqaud.cafe.service.ArticleService;

@RestController
public class CommentCommandController {
	private ArticleService articleService;

	public CommentCommandController(ArticleService articleService) {
		this.articleService = articleService;
	}

	@GetMapping("/comments/{postIndex}")
	public List<Comment> show(@PathVariable Long postIndex) {
		return articleService.showComments(postIndex);
	}

	@PostMapping("/comment/create/{author}")
	public List<Comment> create(CommentDto commentDto) {
		return articleService.createComment(commentDto);
	}

	@DeleteMapping("/comment/delete/{postIndex}/{commentIndex}")
	public List<Comment> delete(@PathVariable Long postIndex, @PathVariable Long commentIndex, HttpSession session) {
		User user = (User)session.getAttribute("sessionUser");
		Comment comment = articleService.findCommentByIndex(postIndex, commentIndex);
		comment.validateAuthor(user.getNickname(), "다른 사람의 댓글은 삭제할 수 없습니다.");
		articleService.deleteComment(postIndex, commentIndex);
		return articleService.showComments(postIndex);
	}
}
