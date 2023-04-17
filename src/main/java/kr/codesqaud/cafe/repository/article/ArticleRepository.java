package kr.codesqaud.cafe.repository.article;

import kr.codesqaud.cafe.domain.Article;

import java.util.List;

public interface ArticleRepository {
    Long save(Article article);
    Article findById(Long id);
    List<Article> findAll();
    Article findWithSurroundingArticles(Long id);
    boolean exist(Long id);
    int edit(Article article);
    int delete(Long id);
}
