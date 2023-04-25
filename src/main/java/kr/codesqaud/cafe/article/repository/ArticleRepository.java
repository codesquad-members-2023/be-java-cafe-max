package kr.codesqaud.cafe.article.repository;

import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.article.domain.Article;
import kr.codesqaud.cafe.mainPage.PaginationDto;

public interface ArticleRepository {

	void save(Article article);

	List<Article> findAll(PaginationDto paginationDto);

	Optional<Article> findArticleByIdx(Long articleIdx);

	void updateArticle(Article article);

	boolean deleteArticle(Long articleIdx, String userId);

	int getCountOfArticles();
}
