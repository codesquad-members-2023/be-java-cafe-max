package kr.codesqaud.cafe.domain.article.repository;

import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.domain.article.entity.Article;

public interface ArticleRepository {

	void save(Article article);

	Optional<Article> findById(Long id);

	List<Article> findAll();

}