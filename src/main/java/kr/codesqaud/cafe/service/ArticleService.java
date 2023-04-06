package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.dto.article.ArticleTimeForm;
import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.article.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void add(Article article) {
        articleRepository.save(article);
    }

    public List<ArticleTimeForm> findArticles() {
        List<Article> articles = articleRepository.findAll();
        List<ArticleTimeForm> articleTimeForms = new ArrayList<>();
        for (Article article : articles) {
            articleTimeForms.add(new ArticleTimeForm(article));
        }
        return articleTimeForms;
    }

    public ArticleTimeForm findArticleId(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("이 아이디를 찾을 수 없어: " + id));
        return new ArticleTimeForm(article);
    }
}
