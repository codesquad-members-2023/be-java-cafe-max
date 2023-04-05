package kr.codesqaud.cafe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.dto.ArticleDto;
import kr.codesqaud.cafe.repository.ArticleRepository;

@Controller
public class ArticleController {
	private final ArticleRepository articleRepository;

	@Autowired
	public ArticleController(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}

	@PostMapping("/qna/write")
	public String writeArticle(ArticleDto articleDto) {
		articleRepository.saveArticle(articleDto);
		return "redirect:/";
	}

	@GetMapping("/articles")
	public String articleList(Model model) {
		List<Article> articles = articleRepository.findAll();
		model.addAttribute("articles", articles);
		return "index";
	}

	@GetMapping("/articles/{index}")
	public String showArticle(@PathVariable("index") Long index, Model model) {
		model.addAttribute("article", articleRepository.findByIndex(index).get());
		return "/qna/detail";
	}
}
