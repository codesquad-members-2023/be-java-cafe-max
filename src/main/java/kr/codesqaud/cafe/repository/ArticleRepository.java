package kr.codesqaud.cafe.repository;

import java.util.List;

import kr.codesqaud.cafe.domain.Article;

public interface ArticleRepository {

	void save(Article article);

	List<Article> findAllPosting();

	Article findPosting(Long id);
}
