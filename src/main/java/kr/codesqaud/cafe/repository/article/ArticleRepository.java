package kr.codesqaud.cafe.repository.article;

import kr.codesqaud.cafe.domain.Article;

import java.util.List;

public interface ArticleRepository {
    Long save(Article article);
    Article findById(Long id);
    List<Article> findAll();
    boolean exist(Long id);
}
