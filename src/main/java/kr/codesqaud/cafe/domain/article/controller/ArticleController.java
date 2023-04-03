package kr.codesqaud.cafe.domain.article.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import kr.codesqaud.cafe.domain.article.dto.request.ArticleSaveRequestDto;
import kr.codesqaud.cafe.domain.article.dto.response.ArticleDetailResponseDto;
import kr.codesqaud.cafe.domain.article.entity.Article;
import kr.codesqaud.cafe.domain.article.repository.ArticleRepository;

@Controller
public class ArticleController {

	private final ArticleRepository articleRepository;

	public ArticleController(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}

	@GetMapping("/")
	public String main(Model model) {
		List<Article> articles = articleRepository.findAll();
		List<ArticleDetailResponseDto> articleDetailResponseDtos = new ArrayList<>();
		articles.forEach(article -> articleDetailResponseDtos.add(new ArticleDetailResponseDto(article)));
		model.addAttribute("articles", articleDetailResponseDtos);
		return "index";
	}

	@PostMapping("/questions")
	public String saveArticle(ArticleSaveRequestDto articleSaveRequestDto) {
		articleRepository.save(articleSaveRequestDto.toEntity());
		return "redirect:/";
	}

	@GetMapping("/questions/{id}")
	public String viewArticle(@PathVariable("id") Long id, Model model) {
		Article article = articleRepository.findById(id).orElseThrow(NoSuchElementException::new);
		model.addAttribute("article", new ArticleDetailResponseDto(article));
		return "/post/show";
	}
}
