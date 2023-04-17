package kr.codesqaud.cafe.article.repository;

import kr.codesqaud.cafe.article.domain.Article;

import java.util.List;


public interface ArticleRepository {

    Long save(Article article);

    List<Article> findAll();

    Article findById(long id);

    Long modify(Article article);

    void delete(long id);
}
