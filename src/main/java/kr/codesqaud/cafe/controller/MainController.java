package kr.codesqaud.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.codesqaud.cafe.service.ArticleService;

@Controller
public class MainController {

	private final ArticleService articleService;

	public MainController(ArticleService articleService) {
		this.articleService = articleService;
	}

	@GetMapping("/")
	public String showMainPage(final Model model) {
		model.addAttribute("articles", articleService.getArticlesWithCommentCount());
		return "index";
	}
}
