package kr.codesqaud.cafe.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.dto.ArticleDto;
import kr.codesqaud.cafe.repository.ArticleRepository;

@Service
public class ArticleService {
	private final ArticleRepository articleRepository;
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public ArticleService(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}

	public boolean createArticle(ArticleDto articleDto) {
		Article article = new Article(articleDto.getTitle(), articleDto.getWriter(),
			articleDto.getContents(), writeDate(), 0L);
		articleRepository.create(article);
		return true;
	}

	private String writeDate() {
		LocalDateTime now = LocalDateTime.now();
		return now.format(DATE_FORMATTER);
	}

	public Article findByIndex(Long index) {
		return articleRepository.findByIndex(index).get();
	}

	public List<Article> findArticles() {
		return articleRepository.findAll();
	}

	public boolean increaseHits(Long index) {
		articleRepository.increaseHits(index);
		return true;
	}

	public boolean deleteArticle(Long index) {
		articleRepository.delete(index);
		return true;
	}
}
