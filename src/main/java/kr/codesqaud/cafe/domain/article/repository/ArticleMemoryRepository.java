package kr.codesqaud.cafe.domain.article.repository;

import kr.codesqaud.cafe.domain.article.Article;

import java.util.ArrayList;
import java.util.List;


public class ArticleMemoryRepository implements ArticleRepository {
    private final List<Article> articles = new ArrayList<>();

    @Override
    public void save(Article article) {
        articles.add(article);
    }

    @Override
    public List<Article> findAll() {
        return articles;
    }

    @Override
    public Article findByIdx(int idx) {
        return null;
    }

    @Override
    public void update(Article article) {}
}
