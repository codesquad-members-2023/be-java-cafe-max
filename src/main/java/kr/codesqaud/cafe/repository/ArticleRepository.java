package kr.codesqaud.cafe.repository;

import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.domain.Article;

public interface ArticleRepository {
	Article save(Article article);

	Optional<Article> findByIndex(Long index);

	Optional<Article> findByTitle(String title);

	Optional<Article> findByContents(String contents);

	List<Article> findAll();

	boolean increaseHits(long index);
}
