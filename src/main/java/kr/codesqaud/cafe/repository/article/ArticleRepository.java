package kr.codesqaud.cafe.repository.article;

import kr.codesqaud.cafe.domain.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    long save(Article article);
    Optional<Article> findById(Long id);
    List<Article> findAll();
    Optional<Article> findWithSurroundingArticles(Long id);
    boolean exist(Long id);
    int edit(Article article);
    int delete(Long id);
}
