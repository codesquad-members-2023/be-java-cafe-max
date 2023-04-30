package kr.codesqaud.cafe.repository;

import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.controller.dto.ArticleWithCommentCount;
import kr.codesqaud.cafe.domain.article.Article;
import kr.codesqaud.cafe.service.paging.Pageable;

public interface ArticleRepository {

	Optional<Article> save(Article article);

	List<Article> findAll();

	List<ArticleWithCommentCount> findAllArticleWithCommentCount(Pageable pageable);

	Optional<Article> findById(Long id);

	void update(Article article);

	void deleteById(Long id);

	boolean isPossibleDeleteById(Long id);

	Long countAllArticles();
}
