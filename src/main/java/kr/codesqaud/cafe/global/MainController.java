package kr.codesqaud.cafe.global;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.codesqaud.cafe.article.ArticleService;
import kr.codesqaud.cafe.article.dto.ArticleResponseForList;

@Controller
public class MainController {
	private final ArticleService articleService;

	public MainController(ArticleService articleService) {
		this.articleService = articleService;
	}

	@GetMapping("/")
	public String mainPage(Model model) {
		List<ArticleResponseForList> articles = articleService.getArticleList();
		model.addAttribute("articles", articles)
			.addAttribute("countOfArticles", articles.size());
		return "index";
	}
}
