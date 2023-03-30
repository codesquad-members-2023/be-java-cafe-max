package kr.codesqaud.cafe.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.domain.Article;

@Repository
public class ArticleRepository {

	private final Map<Long, Article> articleRepository = new HashMap<>();
	private Long sequence = 1L;

	public void save(Article article) {
		article.setId(sequence++);
		articleRepository.put(article.getId(), article);
	}
}
