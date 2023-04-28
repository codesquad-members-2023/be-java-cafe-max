package codesquad.cafe.article.repository;

import codesquad.cafe.article.domain.Article;
import codesquad.cafe.global.util.Criteria;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;


public class MemoryArticleRepository implements ArticleRepository {

    private static Map<Long, Article> store = new ConcurrentHashMap<>();
    private AtomicLong id = new AtomicLong();

    @Override
    public void save(final Article article) {
        this.id.getAndIncrement();
        store.put(this.id.get(), article.createdWith(this.id.get()));
    }

    @Override
    public Article findById(final Long id) {
        return store.get(id);
    }

    @Override
    public String findWriterByUserId(final Article writerId) {
        return null;
    }

    @Override
    public void update(final Article article) {
    }

    @Override
    public void deletePostById(final Long postId) {
    }

    @Override
    public List<Article> findPagingArticles(final Criteria criteria) {
        return null;
    }

    @Override
    public int getTotal() {
        return 0;
    }

}
