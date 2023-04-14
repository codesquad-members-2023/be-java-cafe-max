package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.entity.Article;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class ArticleMemoryRepository implements ArticleRepository{

    private static final Map<Long, Article> articles = new ConcurrentHashMap<>();
    private Long sequence = 0L;

    @Override
    public void save(Article article) {
        articles.put(article.getArticleId(), article);
    }

    @Override
    public List<Article> findAll() {
        return articles.values()
                .stream()
                .map(article -> new Article(
                        article.getArticleId(),
                        article.getWriter(),
                        article.getTitle(),
                        article.getContents()
                        ))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Article> findByArticleId(long articleId) {
        return Optional.ofNullable(articles.get(articleId));
    }

}
