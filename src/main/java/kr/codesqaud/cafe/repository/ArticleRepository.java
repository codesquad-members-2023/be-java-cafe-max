package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    Long save(Article article);

    Optional<Article> findById(Long id);

    List<Article> findAll();
}
