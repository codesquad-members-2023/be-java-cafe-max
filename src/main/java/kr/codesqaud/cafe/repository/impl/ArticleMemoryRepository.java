package kr.codesqaud.cafe.repository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import kr.codesqaud.cafe.domain.article.Article;
import kr.codesqaud.cafe.repository.ArticleRepository;

public class ArticleMemoryRepository implements ArticleRepository {

	private final Map<Long, Article> articleRepository = new HashMap<>();
	private Long sequence = 1L;

	@Override
	public Optional<Article> save(final Article article) {
		article.setId(sequence++);
		articleRepository.put(article.getId(), article);
		return Optional.of(article);
	}

	@Override
	public List<Article> findAll() {
		return new ArrayList<>(articleRepository.values())
			.stream()
			.collect(Collectors.toUnmodifiableList());
	}

	@Override
	public Optional<Article> findById(final Long id) {
		return Optional.ofNullable(articleRepository.get(id));
	}

	@Override
	public void update(final Article article) {
		articleRepository.put(article.getId(), article);
	}

	@Override
	public void deleteById(final Long id) {
		articleRepository.remove(id);
	}
}
