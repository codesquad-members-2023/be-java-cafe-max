package codesquad.cafe.article.repository;

import codesquad.cafe.article.domain.Article;

import java.util.List;

public interface ArticleRepository {
    void save(Article article);

    List<Article> findAll();

    Article findById(Long id);

}
