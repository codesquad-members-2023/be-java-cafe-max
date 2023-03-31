package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {

    Article save(Article article);
    Optional<Article> findByWriter(String writer);
    Optional<Article> findByTitle(String title);
    List<Article> findAll();
}
