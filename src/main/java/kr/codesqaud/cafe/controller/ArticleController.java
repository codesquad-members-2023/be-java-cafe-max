package kr.codesqaud.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import kr.codesqaud.cafe.controller.dto.PostingRequest;
import kr.codesqaud.cafe.service.ArticleService;

@Controller
public class ArticleController {
	private final ArticleService articleService;

	public ArticleController(ArticleService articleService) {
		this.articleService = articleService;
	}

	@PostMapping("/qna/form")
	public String createNewPosting(@ModelAttribute PostingRequest postingRequest) {
		articleService.articleSave(postingRequest);
		return "redirect:/";
	}

	@GetMapping("/")
	public String postingListLookup(Model model) {
		model.addAttribute("articles", articleService.allListLookup());
		return "index";
	}

	@GetMapping("/articles/{id}")
	public String postDetails(Model model, @PathVariable Long id) {
		model.addAttribute("details", articleService.findById(id));
		return "qna/show";
	}
}
