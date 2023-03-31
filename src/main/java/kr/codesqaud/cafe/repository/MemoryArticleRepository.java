package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MemoryArticleRepository implements ArticleRepository {
    private final Map<Long, Article> articleMap = new HashMap<>();
    private static Long sequence = 0L;


    @Override
    public void save(Article article) {
        article.setArticleIndex(++sequence);
        articleMap.put(article.getArticleIndex(), article);
    }

    @Override
    public List<Article> findAll() {
        return new ArrayList<>(articleMap.values());
    }

    @Override
    public Article findByArticleIndex(Long articleIndex) {
        return articleMap.get(articleIndex);
    }
}
