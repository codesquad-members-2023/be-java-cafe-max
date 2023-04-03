package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;

public interface ArticleRepository {
    Long save(Article article);
}
