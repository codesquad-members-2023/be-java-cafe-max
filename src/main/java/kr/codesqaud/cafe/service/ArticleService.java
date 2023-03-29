package kr.codesqaud.cafe.service;

import org.springframework.stereotype.Service;

import kr.codesqaud.cafe.controller.dto.req.PostingRequest;
import kr.codesqaud.cafe.domain.article.Article;
import kr.codesqaud.cafe.repository.ArticleRepository;

@Service
public class ArticleService {

	private final ArticleRepository articleRepository;

	public ArticleService(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}

	public void posting(final PostingRequest request) {
		articleRepository.save(Article.from(request));
	}
}
