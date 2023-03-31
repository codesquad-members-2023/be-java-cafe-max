package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.controller.dto.ArticleDto;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ArticleRepository {
    private Map<Integer,Article> articleRepository;
    private static int sequence = 1;

    public ArticleRepository() {
        this.articleRepository = new HashMap();
    }

    public void save(ArticleDto articleDto){
        articleRepository.put(sequence,articleDto.toArticle(sequence++));
    }

    public List<Article> findAll() {
        List<Article> articles = articleRepository.values().stream()
                .map(article -> new Article(article.getTitle(), article.getContent(), article.getId()))
                .collect(Collectors.toList());
        return Collections.unmodifiableList(articles);
    }

    public ArticleDto findArticle(int id){
        return articleRepository.values().stream()
                .filter(article -> article.getId() == id)
                .findFirst()
                .map(Article::toDto)
                .orElse(null);
    }
}
