package kr.codesqaud.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import kr.codesqaud.cafe.controller.dto.req.PostingRequest;
import kr.codesqaud.cafe.service.ArticleService;

@Controller
public class ArticleController {

	private final ArticleService articleService;

	public ArticleController(ArticleService articleService) {
		this.articleService = articleService;
	}

	@PostMapping("/question")
	public String posting(@ModelAttribute final PostingRequest request) {
		articleService.posting(request);
		return "redirect:/";
	}

	@GetMapping("/articles/{articleId}")
	public String showArticleDetails(@PathVariable final Long articleId, final Model model) {
		model.addAttribute("article", articleService.findById(articleId));
		return "qna/show";
	}
}
