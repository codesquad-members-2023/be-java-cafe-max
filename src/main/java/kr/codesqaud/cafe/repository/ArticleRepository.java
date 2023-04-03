package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;

import java.util.List;

public interface ArticleRepository {
    Article save(Article article);
    Article findById(Long id);
    List<Article> findAll();
    boolean isExists(Long id);
}
