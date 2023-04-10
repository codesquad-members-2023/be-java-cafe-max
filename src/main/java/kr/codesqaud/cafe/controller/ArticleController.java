package kr.codesqaud.cafe.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.dto.ArticleDto;
import kr.codesqaud.cafe.service.ArticleService;

@Controller
public class ArticleController {
	private final ArticleService articleService;

	public ArticleController(ArticleService articleService) {
		this.articleService = articleService;
	}

	@PostMapping("/qna/write")
	public String writeArticle(ArticleDto articleDto) {
		articleService.saveArticle(articleDto);
		return "redirect:/";
	}

	@GetMapping("/articles")
	public String articleList(Model model) {
		List<Article> articles = articleService.findArticles();
		model.addAttribute("articles", articles);
		return "index";
	}

	@GetMapping("/articles/{index}")
	public String showArticle(@PathVariable Long index, Model model) {
		articleService.increaseHits(index);
		model.addAttribute("article", articleService.findByIndex(index).get());
		return "qna/detail";
	}
}
