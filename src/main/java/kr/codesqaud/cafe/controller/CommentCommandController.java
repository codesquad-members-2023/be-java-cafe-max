package kr.codesqaud.cafe.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.codesqaud.cafe.domain.Comment;
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
}
