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

    private Map<String, Article> articleStore = new ConcurrentHashMap<>(); // { 제목 : Article } 저장

    @Override
    public Article save(Article article) {
        articleStore.put(article.getTitle(), article);
        return article;
    }

    @Override
    public Optional<Article> findByWriter(String writer) {
        return articleStore.values().stream()
                .filter(article -> article.getWriter().equals(writer))
                .findAny();
    }

    @Override
    public Optional<Article> findByTitle(String title) {
        return Optional.ofNullable(articleStore.get(title));
    }

    @Override
    public List<Article> findAll() {
        return new ArrayList<>(articleStore.values());
    }

    public void clearStore() {
        articleStore.clear();
    }
}
