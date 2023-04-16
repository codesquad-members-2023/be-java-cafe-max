package kr.codesqaud.cafe.domain.article;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleMemoryRepository implements ArticleRepository {

    private final List<Article> store = new ArrayList<>();
    private static long sequence = 0;

    @Override
    public List<Article> findAll() {
        return Collections.unmodifiableList(store);
    }

    @Override
    public Optional<Article> findById(Long id) {
        return store.stream().filter(article -> article.getId().equals(id)).findFirst();
    }

    @Override
    public Article save(Article article) {
        Article newArticle =
            new Article(nextId(),
                article.getTitle(),
                article.getContent(),
                article.getWriteDate(),
                article.getUser());
        store.add(newArticle);
        return newArticle;
    }

    @Override
    public synchronized int deleteAll() {
        int deleteArticleCount = store.size();
        store.clear();
        sequence = 0;
        return deleteArticleCount;
    }

    private synchronized Long nextId() {
        return ++sequence;
    }
}
