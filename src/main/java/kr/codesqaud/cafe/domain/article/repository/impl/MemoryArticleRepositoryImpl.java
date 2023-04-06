package kr.codesqaud.cafe.domain.article.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import kr.codesqaud.cafe.domain.article.entity.Article;
import kr.codesqaud.cafe.domain.article.repository.ArticleRepository;

public class MemoryArticleRepositoryImpl implements ArticleRepository {
	private Map<Long, Article> articles = new ConcurrentHashMap<>();
	private static AtomicLong id = new AtomicLong();

	public void save(Article article) {
		article.setId(id.getAndIncrement());
		articles.put(article.getId(), article);
	}

	public Optional<Article> findById(Long id) {
		if (articles.get(id) == null) {
			return Optional.empty();
		}
		return Optional.of(articles.get(id));
	}

	public List<Article> findAll() {
		return new ArrayList<>(articles.values());
	}

}
