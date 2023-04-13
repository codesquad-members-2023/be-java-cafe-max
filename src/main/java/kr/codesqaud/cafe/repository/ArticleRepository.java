package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {

    void save(Article article);

    List<Article> findAll();

    Optional<Article> findArticleByIdx(long idx);

    void updateArticle(Article article);
}
