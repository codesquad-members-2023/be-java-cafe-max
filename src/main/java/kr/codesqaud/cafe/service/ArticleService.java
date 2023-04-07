package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.ArticleForm;
import kr.codesqaud.cafe.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    private ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void saveArticle(ArticleForm articleForm) {
        Article article = new Article(articleForm.getWriter(), articleForm.getTitle(), articleForm.getContents());

        articleRepository.save(article);
    }

    public List<Article> findAll() {
        return articleRepository.getAllArticle();
    }

    public Article findArticleIndexOf(int index) {
        return articleRepository.getArticleIndexOf(index);
    }
}
