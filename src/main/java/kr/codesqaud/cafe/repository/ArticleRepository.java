package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository {

    public void save(Article article);
}
