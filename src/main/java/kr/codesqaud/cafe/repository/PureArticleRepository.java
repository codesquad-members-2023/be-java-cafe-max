package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PureArticleRepository implements ArticleRepository{
    private final Map<Long, Article> articles = new ConcurrentHashMap<>();
    private final AtomicLong atomicId = new AtomicLong();

    @Override
    public Long save(Article article) {
        article.setId(atomicId.incrementAndGet());
        articles.put(article.getId(), article);

        return article.getId();
    }
}
