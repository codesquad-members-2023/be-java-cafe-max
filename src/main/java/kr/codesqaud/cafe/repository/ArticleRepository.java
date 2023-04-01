package kr.codesqaud.cafe.repository;

import java.util.List;
import java.util.Optional;
import kr.codesqaud.cafe.domain.Article;

public interface ArticleRepository {

    Article save(Article article);

    Optional<Article> findByIndex(long index);

    List<Article> findAll();
}
