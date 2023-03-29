package kr.codesqaud.cafe.repository;

import java.util.Optional;

import kr.codesqaud.cafe.domain.article.Article;

public interface ArticleRepository {

	Optional<Article> save(Article article);
}
