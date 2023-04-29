package kr.codesqaud.cafe.repository.article;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.vo.PageForm;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    Article save(Article article);

    Optional<Article> findById(Long id);

    List<Article> findAll(PageForm pageForm);

    void update(Long id, Article article);

    void deleteArticle(Long id);

    Long count();
}
