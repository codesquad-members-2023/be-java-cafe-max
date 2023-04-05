package kr.codesqaud.cafe.Repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ArticleRepository {
    private final Map<Integer, Article> articleRepository = new HashMap<>();
    private Integer sequence = 1;

    public Article saveArticle(Article article) {
        articleRepository.put(sequence++, article);
        return article;
    }

    public List<Article> showAllArticle() {
        return  new ArrayList<>(articleRepository.values());
    }


}
