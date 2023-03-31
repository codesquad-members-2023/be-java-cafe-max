package kr.codesqaud.cafe.domain.article.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import kr.codesqaud.cafe.domain.article.dto.ArticleSaveRequestDto;
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

	@PostMapping("/questions")
	public String askQuestions(ArticleSaveRequestDto articleSaveRequestDto) {
		articleRepository.save(articleSaveRequestDto.toEntity());
		return "redirect:/";
	}
}
