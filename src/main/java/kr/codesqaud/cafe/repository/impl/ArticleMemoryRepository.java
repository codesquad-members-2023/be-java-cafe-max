package kr.codesqaud.cafe.repository.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.domain.article.Article;
import kr.codesqaud.cafe.repository.ArticleRepository;

@Repository
public class ArticleMemoryRepository implements ArticleRepository {

	private static Long sequence = 1L;
	private final Map<Long, Article> articleRepository = new HashMap<>();

	@Override
	public Optional<Article> save(Article article) {
		article.setId(sequence++);
		articleRepository.put(article.getId(), article);
		return Optional.of(article);
	}
}
