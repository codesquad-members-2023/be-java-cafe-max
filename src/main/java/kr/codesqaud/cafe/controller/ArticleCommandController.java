package kr.codesqaud.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import kr.codesqaud.cafe.dto.ArticleDto;
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
	public String deleteArticle(@PathVariable Long index) {
		articleService.deleteArticle(index);
		return "redirect:/";
	}
}
