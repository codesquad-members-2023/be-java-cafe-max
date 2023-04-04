package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryArticleRepository implements ArticleRepository {
    private final Map<Long, Article> articleMap = new HashMap<>();
    private static Long sequence = 0L;

    @Override
    public void save(Article article) {
        article.setId(++sequence);
        articleMap.put(article.getId(), article);
    }

    @Override
    public List<Article> findAll() {
        return new ArrayList<>(articleMap.values());
    }

    @Override
    public Optional<Article> findById(Long id) {
        return Optional.ofNullable(articleMap.get(id));
    }
}
