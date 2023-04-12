package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MemoryArticleRepository implements ArticleRepository {

    private Map<Long, Article> articleStore = new ConcurrentHashMap<>(); // { id : Article } 저장

    private static AtomicLong sequence = new AtomicLong(0L);

    @Override
    public Article save(Article article) {
        long id = sequence.incrementAndGet();
        article.setId(id);
        articleStore.put(id, article);
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

    @Override
    public void updateWriter(String name, String updateName) {
        // 작성자 이름 변경
    }

    @Override
    public boolean isCreatedBy(String userId, Long id) {
        // userId가 article을 작성한 사용자인지 확인
        return false;
    }

    @Override
    public void updateTitle(Long id, String updateTitle) {

    }

    @Override
    public void updateContents(Long id, String updateContents) {

    }

    public void clearStore() {
        articleStore.clear();
    }
}
