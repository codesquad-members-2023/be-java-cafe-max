package kr.codesqaud.cafe.repository.article;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class MemoryArticleRepository implements ArticleRepository {

    private final Map<Long, Article> STORE = new ConcurrentHashMap<>();
    private static AtomicLong sequence = new AtomicLong();

    @Override
    public Long save(Article article) {
        article.setId(sequence.incrementAndGet());
        STORE.put(article.getId(), article);
        return article.getId();
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
    public boolean exist(Long id) { return STORE.containsKey(id); }

    @Override
    public Article findWithSurroundingArticles(Long id) {
        final Long previousId = STORE.keySet().stream().filter(key -> key > id).max(Comparator.naturalOrder()).orElse(0L);
        final Long nextId = STORE.keySet().stream().filter(key -> key < id).min(Comparator.naturalOrder()).orElse(0L);
        Article article = STORE.get(id);
        article.setPreviousId(previousId);
        article.setNextId(nextId);
        return article;
    }
}
