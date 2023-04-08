package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class MemoryArticleRepositoryImpl implements ArticleRepository{
    private final Map<Long, Article> articles = new ConcurrentHashMap<>();
    private final AtomicLong atomicId = new AtomicLong();

    @Override
    public Long save(Article article) {
        article.setId(atomicId.incrementAndGet());
        articles.put(article.getId(), article);

        return article.getId();
    }

    @Override
    public Optional<Article> findById(Long id) {
        return Optional.ofNullable(articles.get(id));
    }

    @Override
    public List<Article> findAll() {
        return articles.values().stream()
                .sorted(Comparator.comparing(Article::getId).reversed())
                .collect(Collectors.toList());
    }
}
