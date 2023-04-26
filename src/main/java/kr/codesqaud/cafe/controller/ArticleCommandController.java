package kr.codesqaud.cafe.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import kr.codesqaud.cafe.dto.ArticleRequest;
import kr.codesqaud.cafe.dto.UserRequest;
import kr.codesqaud.cafe.service.ArticleService;

@Controller
public class ArticleCommandController {
	private final ArticleService articleService;

	public ArticleCommandController(ArticleService articleService) {
		this.articleService = articleService;
	}

	@PostMapping("/articles")
	public String writeArticle(@Valid ArticleRequest articleRequest) {
		articleService.createArticle(articleRequest);
		return "redirect:/";
	}

	@DeleteMapping("/articles/{articleIndex}")
	public String deleteArticle(@PathVariable Long articleIndex, HttpSession session) {
		articleService.checkWriterEqualsSessionUser((UserRequest)session.getAttribute("sessionUser"), articleIndex);
		articleService.deleteArticle(articleIndex);
		articleService.deleteAllComment(articleIndex);
		return "redirect:/";
	}

	@PatchMapping("/articles/{articleIndex}")
	public String updateArticle(@PathVariable Long articleIndex,
		@Valid ArticleRequest articleRequest) {
		articleService.updateArticle(articleIndex, articleRequest);
		return "redirect:/";
	}
}
