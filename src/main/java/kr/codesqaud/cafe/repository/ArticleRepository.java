package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    public void save(Article article);

    public Optional<Article> getArticleById(Long id);

    public List<Article> getArticleList();

    public void clearStore();
}




