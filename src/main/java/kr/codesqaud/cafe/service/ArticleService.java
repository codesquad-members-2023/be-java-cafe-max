package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }
    public String write(Article article) {
        validateDuplicateTitle(article);
        articleRepository.save(article);
        return article.getTitle();
    }
    private void validateDuplicateTitle(Article article) {
        articleRepository.findByTitle(article.getTitle())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 글입니다");
                });
    }

    public List<Article> findArticles() {
        return articleRepository.findAll();
    }

    public Optional<Article> findOne(String title) {
        return articleRepository.findByTitle(title);
    }
}
