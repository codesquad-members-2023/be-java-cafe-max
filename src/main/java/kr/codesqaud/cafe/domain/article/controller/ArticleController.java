package kr.codesqaud.cafe.domain.article.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.codesqaud.cafe.domain.article.repository.ArticleRepository;

@Controller
public class ArticleController {

	private final ArticleRepository articleRepository;

	public ArticleController(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}

	@GetMapping("/")
	public String main(Model model) {
		model.addAttribute("articles", articleRepository.findAll());
		return "index";
	}
}
