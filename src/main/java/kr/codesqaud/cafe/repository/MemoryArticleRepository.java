package kr.codesqaud.cafe.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import kr.codesqaud.cafe.domain.Article;

public class MemoryArticleRepository implements ArticleRepository {

	private final Map<Long, Article> articleRepository = new HashMap<>();
	private Long sequence = 1L;

	@Override
	public void save(Article article) {
		article.setId(sequence++);
		articleRepository.put(article.getId(), article);
	}

	@Override
	public List<Article> findAllPosting() {
		return new ArrayList<>(articleRepository.values()).stream().collect(Collectors.toUnmodifiableList());
	}

	@Override
	public Article findPosting(Long id) {
		return articleRepository.get(id);
	}
}
