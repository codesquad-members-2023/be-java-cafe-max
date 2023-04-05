package kr.codesqaud.cafe.repository.article;

import kr.codesqaud.cafe.domain.Article;

import java.util.List;

public interface ArticleRepository {
    void save(Article article);
    Article findById(Long id);
    List<Article> findAll();
    boolean isExists(Long id);
}
