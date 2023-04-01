package kr.codesqaud.cafe.domain.question;

import java.util.List;

public interface ArticleRepository {
    void save(Article article);

    List<Article> findAll();
}
