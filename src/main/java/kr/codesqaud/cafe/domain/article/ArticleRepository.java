package kr.codesqaud.cafe.domain.article;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleRepository {

    // Article 객체 리스트
    private final List<Article> store = new ArrayList<>();
    private static long sequence = 0;

    public List<Article> findAll() {
        return Collections.unmodifiableList(store);
    }

    public Optional<Article> findById(Long id) {
        return store.stream().filter(article -> article.getId().equals(id)).findFirst();
    }

    public Article save(Article article) {
        store.add(article);
        return article;
    }

    public synchronized int deleteAll() {
        int deleteArticleCount = store.size();
        store.clear();
        sequence = 0;
        return deleteArticleCount;
    }

    public synchronized Long nextId() {
        return ++sequence;
    }
}
