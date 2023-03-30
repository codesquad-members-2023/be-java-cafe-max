package kr.codesqaud.cafe.service;

import org.springframework.stereotype.Service;

import kr.codesqaud.cafe.controller.dto.PostingRequest;
import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.ArticleRepository;

@Service
public class ArticleService {

	private final ArticleRepository articleRepository;

	public ArticleService(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}

	public void articleSave(PostingRequest postingRequest) {
		Article article = new Article(
			postingRequest.getWriter(), postingRequest.getTitle(), postingRequest.getContents());
		articleRepository.save(article);
	}
}
