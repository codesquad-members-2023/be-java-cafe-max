package kr.codesqaud.cafe.controller;

import javax.servlet.http.HttpSession;

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

	@PostMapping("/qna/write")
	public String writeArticle(ArticleDto articleDto) {
		articleService.createArticle(articleDto);
		return "redirect:/";
	}

	@DeleteMapping("/qna/delete/{index}")
	public String deleteArticle(@PathVariable Long index, Model model, HttpSession session) {
		User user = (User)session.getAttribute("sessionUser");
		Article article = articleService.findByIndex(index);
		article.validateWriter(user.getNickname(), "다른 사람의 글은 삭제할 수 없습니다.");
		articleService.deleteArticle(index);
		return "redirect:/";
	}

	@PatchMapping("/qna/update/{index}")
	public String updateArticle(@PathVariable Long index, ArticleDto articleDto) {
		articleService.updateArticle(index, articleDto);
		return "redirect:/";
	}

	@PostMapping("/comment/create/{writer}")
	public String createComment(@PathVariable String writer, CommentDto commentDto) {
		articleService.createComment(commentDto);
		return "redirect:/articles/" + commentDto.getPostIndex() + "/" + writer;
	}

	@DeleteMapping("/comment/delete/{postIndex}/{index}/{writer}")
	public String deleteComment(@PathVariable Long postIndex, @PathVariable Long index, @PathVariable String writer,
		HttpSession session) {
		User user = (User)session.getAttribute("sessionUser");
		Comment comment = articleService.findCommentByIndex(postIndex, index);
		comment.validateAuthor(user.getNickname());
		articleService.deleteComment(postIndex, index);
		return "redirect:/articles/" + postIndex + "/" + writer;
	}
}
