package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.User;

import java.util.List;

public interface ArticleRepository {
    public Article save(Article article);
    public List<Article> findAll();
    public void clearArticleList();
}
