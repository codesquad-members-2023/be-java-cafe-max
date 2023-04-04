package codesquad.cafe.repository;

import codesquad.cafe.domain.Article;
import org.springframework.stereotype.Repository;

public interface ArticleRepository {
    void save(Article article);
}
