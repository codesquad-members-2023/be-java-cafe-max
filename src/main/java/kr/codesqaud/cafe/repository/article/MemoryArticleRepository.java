package kr.codesqaud.cafe.repository.article;

import kr.codesqaud.cafe.domain.Article;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MemoryArticleRepository implements ArticleRepository {

    private final Map<Long, Article> STORE = new ConcurrentHashMap<>();
    private static AtomicLong sequence = new AtomicLong();

    @Override
    public void save(Article article) {
        article.setIdUsingSequence(sequence.incrementAndGet());
        STORE.put(article.getId(), article);
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
