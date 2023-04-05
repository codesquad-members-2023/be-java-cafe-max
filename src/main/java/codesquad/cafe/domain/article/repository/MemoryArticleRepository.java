package codesquad.cafe.domain.article.repository;

import codesquad.cafe.domain.article.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class MemoryArticleRepository implements ArticleRepository {

    private static Map<Long, Article> store = new ConcurrentHashMap<>();
    private AtomicLong id = new AtomicLong();

    @Override
    public void save(final Article article) {
        id.getAndIncrement();
        store.put(id.get(), article.createdWith(id.get()));
    }

    @Override
    public List<Article> findAll() {
        return store.values().stream().collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Article findById(final Long id) {
        return store.get(id);
    }

}
