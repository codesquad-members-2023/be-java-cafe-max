package kr.codesqaud.cafe.domain.article.repository.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.domain.article.entity.Article;
import kr.codesqaud.cafe.domain.article.repository.ArticleRepository;

@Repository
public class MemoryArticleRepositoryImpl implements ArticleRepository {
	private Map<Integer, Article> articles = new ConcurrentHashMap<>();
}
