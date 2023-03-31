package kr.codesqaud.cafe.domain.article.repository;

import java.util.List;

import kr.codesqaud.cafe.domain.article.entity.Article;

public interface ArticleRepository {

	void save(Article article);

	Article findById(Integer id);

	List<Article> findAll();

}