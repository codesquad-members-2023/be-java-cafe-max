package kr.codesqaud.cafe.service;

import java.util.ArrayList;
import java.util.List;

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
		Article article = postingRequest.getArticleEntity();
		articleRepository.save(article);
	}

	public List<Article> allListLookup() {
		List<Article> allPosting = articleRepository.findAllPosting();
		List<Article> postings = new ArrayList<>();
		for (int i = 0; i < allPosting.size(); i++) {
			Article article = allPosting.get(i);
			postings.add(new Article(article.getWriter(), article.getTitle(), article.getContents()));
		}
		return postings;
	}
}
