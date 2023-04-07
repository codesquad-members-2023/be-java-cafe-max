package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ArticleMemoryRepository implements ArticleRepository{

    private static final Map<Long, Article> articles = new ConcurrentHashMap<>();
    private Long sequence = 0L;

    @Override
    public Article save(Article article) {
        article.setArticleId(++sequence);
        articles.put(article.getArticleId(), article);
        return article;
    }

    @Override
    public List<Article> findAll() {
        return new ArrayList<>(articles.values());
    }

    @Override
    public Optional<Article> findByArticleId(long articleId) {
        return Optional.ofNullable(articles.get(articleId));
    }

    @Override
    public void clearArticleList() {
        articles.clear();
    }

}
