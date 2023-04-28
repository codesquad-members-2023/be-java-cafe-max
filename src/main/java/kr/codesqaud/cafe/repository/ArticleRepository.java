package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.controller.dto.request.articleRequest.ArticleWithReplyCount;
import kr.codesqaud.cafe.domain.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    Article save(Article article);

    List<Article> findAll();

    List<ArticleWithReplyCount> findAllArticlesWithReplyCount();

    Optional<Article> findById(Long id);

    void delete(Long id);

    void update(Article article);
}
