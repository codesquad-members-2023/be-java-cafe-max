package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.Repository.ArticleRepository;
import kr.codesqaud.cafe.domain.Article;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = new ArticleRepository();
    }

    public Article saveArticle(Article article) {
        articleRepository.saveArticle(article);
        return article;
    }

    public List<Article> findArticles() {
        return articleRepository.showAllArticle();
    }
}
