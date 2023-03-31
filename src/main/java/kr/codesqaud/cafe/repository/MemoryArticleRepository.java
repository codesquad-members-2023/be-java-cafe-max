package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MemoryArticleRepository implements ArticleRepository {
    private final Map<String, Article> articleMap = new HashMap<>();

    @Override
    public void save(Article article) {
        if (articleMap.containsKey(article.getWriter())) {
            return;
        }

        articleMap.put(article.getWriter(), article);
    }

    @Override
    public Article findByName(String name) {
        return articleMap.get(name);
    }

    @Override
    public List<Article> findAll() {
        return new ArrayList<>(articleMap.values());
    }
}
