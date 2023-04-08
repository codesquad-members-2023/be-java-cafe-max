package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class MemoryArticleRepository implements ArticleRepository{
    private static final Map<Long, Article> store = new ConcurrentHashMap<>();
    private static final AtomicLong articleId = new AtomicLong(0);
    @Override
    public Article save(Article article) {
        article.setId(articleId.incrementAndGet());
        store.put(article.getId(), article);
        return article;
    }

    @Override
    public Optional<Article> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Article> findAll() {
        return store.values().stream().collect(Collectors.toUnmodifiableList());
    }

    public void clearStore() {store.clear();}
}
