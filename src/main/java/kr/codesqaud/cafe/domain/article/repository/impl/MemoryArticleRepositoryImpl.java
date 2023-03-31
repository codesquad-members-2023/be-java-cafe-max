package kr.codesqaud.cafe.domain.article.repository.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.domain.article.entity.Article;
import kr.codesqaud.cafe.domain.article.repository.ArticleRepository;

@Repository
public class MemoryArticleRepositoryImpl implements ArticleRepository {
	private Map<Integer, Article> articles = new ConcurrentHashMap<>();
	private static Integer autoIncrement = 0;

	public void save(Article article) {
		article.setId(++autoIncrement);
		articles.put(article.getId(), article);
	}

	public Article findById(Integer id) {
		return articles.get(id);
	}

}
