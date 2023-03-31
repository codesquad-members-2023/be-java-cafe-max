package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ArticleMemoryRepository implements ArticleRepository{

    private static final List<Article> articleList = new ArrayList<>();

    @Override
    public Article save(Article article) {
        articleList.add(article);
        return article;
    }

    @Override
    public List<Article> findAll() {
        return new ArrayList<>(articleList);
    }

    @Override
    public void clearArticleList() {
        articleList.clear();
    }
}
