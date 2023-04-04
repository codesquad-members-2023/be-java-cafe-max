package codesquad.cafe.repository;

import codesquad.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class MemoryArticleRepository implements ArticleRepository {

    private static Map<AtomicLong, Article> store = new ConcurrentHashMap<>();
    private AtomicLong id = new AtomicLong();

    @Override
    public void save(final Article article) {
        store.put(id, article);
        id.getAndIncrement();
    }
}
