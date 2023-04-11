package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class MemoryArticleRepository implements ArticleRepository {

    private static final Map<Long, Article> articleRepository = new ConcurrentHashMap<>();
    private static final AtomicLong sequence = new AtomicLong(0L);

    @Override
    public Optional<Article> save(Article article) {
        long newId = sequence.incrementAndGet();
        article.setId(newId);
        articleRepository.put(article.getId(), article);
        return Optional.of(article);
    }

    @Override
    public List<Article> findAll() {
        return new ArrayList<>(articleRepository.values());
    }

    @Override
    public Optional<Article> findById(Long id) {
        return Optional.ofNullable(articleRepository.get(id));
    }
}
