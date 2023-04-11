package kr.codesqaud.cafe.repository;

import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.domain.Article;

public interface ArticleRepository {
	Article create(Article article);

	Optional<Article> findByIndex(Long index);

	List<Article> findAll();

	boolean increaseHits(Long index);

	boolean delete(Long index);
}
