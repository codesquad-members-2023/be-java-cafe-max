package kr.codesqaud.cafe.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

	public List<Article> findAllPosting() {
		return new ArrayList<>(articleRepository.values()).stream().collect(Collectors.toUnmodifiableList());
	}
}
