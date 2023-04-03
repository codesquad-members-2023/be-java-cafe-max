package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryArticleRepository implements ArticleRepository {

    private final Map<Long, Article> STORE = new ConcurrentHashMap<>();
    private static Long sequence = 0L;

    @Override
    public Article save(Article article) {
        article.setIdUsingSequence(++sequence);
        STORE.put(article.getId(), article);
        return article;
    }

    @Override
    public Article findById(Long id) {
        return STORE.get(id);
    }

    @Override
    public List<Article> findAll() {
        return new ArrayList<>(STORE.values());
    }

    @Override
    public boolean isExists(Long id) { return STORE.containsKey(id); }
}
