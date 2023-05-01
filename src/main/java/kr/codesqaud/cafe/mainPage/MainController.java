package kr.codesqaud.cafe.mainPage;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import kr.codesqaud.cafe.article.ArticleService;
import kr.codesqaud.cafe.article.dto.ArticleResponseForList;

@Controller
public class MainController {
	private final ArticleService articleService;

	public MainController(ArticleService articleService) {
		this.articleService = articleService;
	}

	@GetMapping("/")
	public String mainPage(Model model, @ModelAttribute PaginationDto paginationDto) {
		List<ArticleResponseForList> articles = articleService.getArticleList(paginationDto);
		Long countOfArticles = articleService.getCountOfArticles();

		model.addAttribute("articles", articles)
			.addAttribute("countOfArticles", countOfArticles)
			.addAttribute("pagination", new Pagination(paginationDto, countOfArticles));
		return "index";
	}
}
