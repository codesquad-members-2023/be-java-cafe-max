package kr.codesqaud.cafe.global;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.codesqaud.cafe.article.ArticleService;

@Controller
public class MainController {
	private final ArticleService articleService;

	public MainController(ArticleService articleService) {
		this.articleService = articleService;
	}

	@GetMapping("/")
	public String mainPage(Model model) {
		model.addAttribute("articleList", articleService.getArticleList());
		return "index";
	}
}
