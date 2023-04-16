package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    Optional<Article> save(Article article);

    List<Article> findAll();

    Optional<Article> findById(Long id);
}
