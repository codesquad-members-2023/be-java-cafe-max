package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ArticleMemoryRepository implements ArticleRepository{

    private static final Map<Long, Article> articleList = new ConcurrentHashMap<>();
    private Long sequence = 0L;

    @Override
    public Article save(Article article) {
        article.setId(++sequence);
        articleList.put(article.getId(), article);
        return article;
    }

    @Override
    public List<Article> findAll() {
        return new ArrayList<>(articleList.values());
    }

    @Override
    public Article findByIndex(long index) {
        return articleList.get(index);
    }

    @Override
    public void clearArticleList() {
        articleList.clear();
    }

}
