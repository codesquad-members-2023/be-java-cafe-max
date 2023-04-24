package kr.codesqaud.cafe.repository.article;

import kr.codesqaud.cafe.controller.article.ArticleForm;
import kr.codesqaud.cafe.domain.Article;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class MemoryArticleRepository implements ArticleRepository{
    private static final Map<Long, Article> store = new ConcurrentHashMap<>();
    private static final AtomicLong articleId = new AtomicLong(0);
    @Override
    public Article save(Article article) {
        article.setId(articleId.incrementAndGet());
        store.put(article.getId(), article);
        return store.get(article.getId());
    }

    @Override
    public Optional<Article> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Article> findAll() {
        return store.values().stream().collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Optional<Article> update(Long id, ArticleForm form) {
        Article article = store.get(id);
        article.setTitle(form.getTitle());
        article.setContents(form.getContents());
        return Optional.of(article);
    }

    @Override
    public Long delete(Long id) {
        Article article = store.get(id);
        article.setDeleted(true);
        return article.getId();
    }

    @Override
    public void clearStore() {store.clear();}
}
