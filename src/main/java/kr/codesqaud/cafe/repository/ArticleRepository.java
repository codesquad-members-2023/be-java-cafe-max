package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {

    Article save(Article article);

    Optional<Article> findByWriter(String writer);

    Optional<Article> findById(Long id);

    List<Article> findAll();

    void updateWriter(String name, String updateName);

    void updateArticle(Article article);

    void delete(Long id);
}
