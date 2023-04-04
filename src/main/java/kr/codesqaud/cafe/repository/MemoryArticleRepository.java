package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryArticleRepository implements ArticleRepository {

    private Map<Long, Article> articleStore = new ConcurrentHashMap<>(); // { id : Article } 저장

    private static  long sequence = 0L;

    @Override
    public Article save(Article article) {
        article.setId(++sequence);
        articleStore.put(article.getId(), article);
        return article;
    }

    @Override
    public Optional<Article> findByWriter(String writer) {
        return articleStore.values().stream()
                .filter(article -> article.getWriter().equals(writer))
                .findAny();
    }

    @Override
    public Optional<Article> findById(Long id) {
        return Optional.ofNullable(articleStore.get(id));
    }

    @Override
    public List<Article> findAll() {
        return new ArrayList<>(articleStore.values());
    }

    public void clearStore() {
        articleStore.clear();
    }
}
