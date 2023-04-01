package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.exception.ArticleNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class ArticleRepository {
    private Map<Integer,Article> articleRepository;
    private static int sequence = 1;
    public ArticleRepository() {
        this.articleRepository = new HashMap();
    }

    public void save(Article article){
        article.setId(sequence++);
        articleRepository.put(sequence,article);
    }

    public List<Article> findAll() {
        List<Article> articles = articleRepository.values().stream()
                .collect(Collectors.toList());
        return Collections.unmodifiableList(articles);
    }

    public Article findArticle(int id){
        return articleRepository.values().stream()
                .filter(article -> article.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ArticleNotFoundException("해당 글이 존재하지 않습니다."));
    }
}
