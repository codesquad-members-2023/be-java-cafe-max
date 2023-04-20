package kr.codesqaud.cafe.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.Comment;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.ArticleDto;
import kr.codesqaud.cafe.dto.CommentDto;
import kr.codesqaud.cafe.service.ArticleService;

@Controller
public class ArticleCommandController {
	private final ArticleService articleService;

	public ArticleCommandController(ArticleService articleService) {
		this.articleService = articleService;
	}

	@PostMapping("/article/write")
	public String writeArticle(@Valid ArticleDto articleDto) {
		articleService.createArticle(articleDto);
		return "redirect:/";
	}

	@DeleteMapping("/article/delete/{index}")
	public String deleteArticle(@PathVariable Long index, Model model, HttpSession session) {
		User user = (User)session.getAttribute("sessionUser");
		Article article = articleService.findByIndex(index);
		article.validateWriter(user.getNickname(), "다른 사람의 글은 삭제할 수 없습니다.");
		List<Comment> comments = articleService.findCommentsByPostIndex(index);
		for (Comment comment : comments) {
			comment.validateAuthors(user.getNickname());
		}
		articleService.deleteArticle(index);
		articleService.deleteAllComment(index);
		return "redirect:/";
	}

	@PatchMapping("/article/update/{index}")
	public String updateArticle(@PathVariable Long index, @Valid ArticleDto articleDto) {
		articleService.updateArticle(index, articleDto);
		return "redirect:/";
	}

	@PostMapping("/comment/create/{writer}")
	public String createComment(@PathVariable String writer, CommentDto commentDto) {
		articleService.createComment(commentDto);
		return "redirect:/article/" + commentDto.getPostIndex() + "/" + writer;
	}

	@DeleteMapping("/comment/delete/{postIndex}/{index}/{writer}")
	public String deleteComment(@PathVariable Long postIndex, @PathVariable Long index, @PathVariable String writer,
		HttpSession session) {
		User user = (User)session.getAttribute("sessionUser");
		Comment comment = articleService.findCommentByIndex(postIndex, index);
		comment.validateAuthor(user.getNickname());
		articleService.deleteComment(postIndex, index);
		return "redirect:/article/" + postIndex + "/" + writer;
	}
}
