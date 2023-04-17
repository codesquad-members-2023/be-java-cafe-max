package kr.codesqaud.cafe.repository;

import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.domain.Article;

public interface ArticleRepository {

	void save(Article article);

	List<Article> findAll();

	Optional<Article> findArticleByIdx(Long idx);

	void updateArticle(Article article);

	void deleteArticle(Long idx);
}
