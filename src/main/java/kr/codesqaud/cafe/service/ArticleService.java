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

    public Article findArticleBySequence(Integer sequence) {
        Article article = articleRepository.findArticleBySequence(sequence).orElseThrow(
                () -> new IllegalArgumentException("해당하는 게시글이 없습니다."));
        return article;
    }
}



