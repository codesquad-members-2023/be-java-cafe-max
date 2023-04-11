package kr.codesqaud.cafe.Repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryArticleRepository implements ArticleRepository {
    private final List<Article> articleRepository = new ArrayList<>();
    //    private final Map<Integer, Article> articleRepository = new HashMap<>();
    private Integer sequence = 1;

    public void saveArticle(final Article article) {
        articleRepository.add(article);
        article.setId(sequence++);
    }

    public List<Article> findAllArticle() {
        return Collections.unmodifiableList(new ArrayList<>(articleRepository));
    }

    public Optional<Article> findArticleById(final int articleId) {
        return Optional.ofNullable(articleRepository.get(articleId - 1));
    }


}
