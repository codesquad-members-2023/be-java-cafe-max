package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.dto.SimpleArticle;
import kr.codesqaud.cafe.pagination.Paging;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {

    Article save(Article article);

    Optional<Article> findByWriter(String writer);

    Optional<Article> findById(Long id);

    List<SimpleArticle> findAll(Paging paging);

    void updateWriter(String name, String updateName);

    void updateArticle(Article article);

    void delete(Long id);

    int count();
}
