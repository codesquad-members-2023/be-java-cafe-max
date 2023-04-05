package kr.codesqaud.cafe.Repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ArticleRepository {
    private final List<Article> articleRepository = new ArrayList<>();
    //    private final Map<Integer, Article> articleRepository = new HashMap<>();
    private Integer sequence = 1;

    public Article saveArticle(Article article) {
        article.setId(sequence);
        articleRepository.add(article);
        sequence++;
        return article;
//        article.setId(sequence);
//        articleRepository.put(sequence++, article);
//        return article;
    }

    public List<Article> showAllArticle() {
        return new ArrayList<>(articleRepository);
    }

    public Optional<Article> findArticleById(int articleId) {
        return Optional.ofNullable(articleRepository.get(articleId - 1));
    }


}
