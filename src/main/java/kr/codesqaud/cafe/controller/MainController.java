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
	public String showArticleList(Model model) {
		model.addAttribute("postList", articleService.getArticleList());
		return "index";
	}
}
