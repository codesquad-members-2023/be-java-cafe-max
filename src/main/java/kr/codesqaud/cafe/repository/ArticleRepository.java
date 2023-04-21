package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;

import java.util.List;

public interface ArticleRepository {
    public void save(Article article);

    public List<Article> findAll();

    public Article findById(long id);
}
