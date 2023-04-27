package kr.codesqaud.cafe.repository.article;

import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.dto.ArticleRequest;

public interface ArticleRepository {
	Article create(Article article);

	Optional<Article> findByArticleIndex(Long articleIndex);

	List<Article> findPage(int page);

	boolean increaseHits(Long index);

	boolean delete(Long index);

	boolean update(Long index, ArticleRequest articleRequest);

	Article findWriterByArticleIndex(Long articleIndex);

	Integer getArticleSize();
}
