package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class MemoryArticleRepository implements ArticleRepository {

    private final static Map<Long, Article> articleMap = new ConcurrentHashMap<>();
    private final static AtomicLong articleSequence = new AtomicLong();
    @Override
    public void save(Article article) {
        article.setId(articleSequence.incrementAndGet());
        articleMap.put(article.getId(), article);
    }

    @Override
    public List<Article> gatherAll() {
        return new ArrayList<>(articleMap.values())
                .stream()
                .sorted(Comparator.comparing(Article::getId).reversed())
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Optional<Article> findById(long id) {
        return Optional.ofNullable(articleMap.get(id));
    }
}
