package kr.codesqaud.cafe.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.dto.ArticleDto;
import kr.codesqaud.cafe.repository.ArticleRepository;

public class ArticleService {
	private final ArticleRepository articleRepository;
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private long index = 0L;

	public ArticleService(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}

	public boolean saveArticle(ArticleDto articleDto) {
		Article article = new Article(increaseIndex(), articleDto.getTitle(), articleDto.getWriter(),
			articleDto.getContents(), writeDate(), 0L);
		articleRepository.save(article);
		return true;
	}

	private String writeDate() {
		LocalDateTime now = LocalDateTime.now();
		return now.format(DATE_FORMATTER);
	}

	private long increaseIndex() {
		return ++index;
	}

	public Optional<Article> findByIndex(Long index) {
		return articleRepository.findByIndex(index);
	}

	public Optional<Article> findByTitle(String title) {
		return articleRepository.findByTitle(title);
	}

	public Optional<Article> findByContents(String contents) {
		return articleRepository.findByContents(contents);
	}

	public List<Article> findArticles() {
		return articleRepository.findAll();
	}
}
