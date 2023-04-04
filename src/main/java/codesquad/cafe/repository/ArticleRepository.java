package codesquad.cafe.repository;

import codesquad.cafe.domain.Article;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public interface ArticleRepository {
    void save(Article article);

    Map<AtomicLong, Article> findAll();
}
