package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

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
        return new ArrayList<>(articleMap.values());
    }
}
