package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    Article save(Article article);

    List<Article> findAll();

    Optional<Article> findById(Long id);

    void delete(Long id);

    void update(Article article);
}
