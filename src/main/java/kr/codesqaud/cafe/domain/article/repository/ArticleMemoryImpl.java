package kr.codesqaud.cafe.domain.article.repository;

import kr.codesqaud.cafe.domain.article.Article;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ArticleMemoryImpl implements ArticleRepository {
    private final List<Article> articles = new ArrayList<>();

    @Override
    public void save(Article article) {
        articles.add(article);
    }

    @Override
    public List<Article> findAll() {
        return articles;
    }
}
