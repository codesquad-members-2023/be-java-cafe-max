package kr.codesqaud.cafe.domain.article.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	public String saveArticle(ArticleSaveRequestDto articleSaveRequestDto) {
		articleRepository.save(articleSaveRequestDto.toEntity());
		return "redirect:/";
	}

	@GetMapping("/questions/{id}")
	public String viewArticle(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("article", articleRepository.findById(id));
		return "/post/show";
	}
}
