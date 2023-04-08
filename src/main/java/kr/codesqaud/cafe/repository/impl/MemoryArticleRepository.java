package kr.codesqaud.cafe.repository.impl;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.ArticleRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Primary
@Repository
public class MemoryArticleRepository implements ArticleRepository {
    private final Map<Integer,Article> articleRepository;
    private int sequence = 1;
    public MemoryArticleRepository() {
        this.articleRepository = new LinkedHashMap();
    }

    @Override
    public void save(Article article){
        article.setId(sequence++);
        articleRepository.put(sequence,article);
    }

    @Override
    public List<Article> findAll() {
        return articleRepository.values().stream()
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Optional<Article> findArticleById(int id){
        return articleRepository.values().stream()
                .filter(article -> article.getId() == id)
                .findFirst();
    }
}
