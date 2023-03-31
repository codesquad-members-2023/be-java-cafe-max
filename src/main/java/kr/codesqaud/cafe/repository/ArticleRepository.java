package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;

import java.util.List;

public interface ArticleRepository {
    void save(Article article);

    Article findByName(String name);

    List<Article> findAll();
}
