package kr.codesqaud.cafe.domain.article.controller;

import org.springframework.stereotype.Controller;

import kr.codesqaud.cafe.domain.article.repository.ArticleRepository;

@Controller
public class ArticleController {

	private final ArticleRepository articleRepository;

	public ArticleController(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}
}
