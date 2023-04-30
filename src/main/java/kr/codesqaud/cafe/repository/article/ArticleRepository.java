package kr.codesqaud.cafe.repository.article;

import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.domain.Article;

public interface ArticleRepository {
	Article create(Article article);

	Optional<Article> findByArticleIndex(Long articleIndex);

	List<Article> findPage(int page);

	boolean increaseHits(Long index);

	boolean delete(Long index);

	boolean update(Long articleIndex, Article article);

	Article findWriterByArticleIndex(Long articleIndex);

	Integer getArticleSize();
}
