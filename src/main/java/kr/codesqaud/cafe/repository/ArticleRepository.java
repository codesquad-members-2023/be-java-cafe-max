package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    public Article save(Article article);
    public List<Article> findAll();
    public Optional<Article> findByArticleId(long articleId);
    public void clearArticleList();
}
