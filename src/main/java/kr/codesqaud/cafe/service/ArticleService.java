package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.ArticleForm;
import kr.codesqaud.cafe.repository.ArticleRepository;
import kr.codesqaud.cafe.repository.JdbcArticleRepository;
import kr.codesqaud.cafe.repository.MemoryArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    private ArticleRepository articleRepository;

    public ArticleService(JdbcArticleRepository jdbcArticleRepository) {
        this.articleRepository = jdbcArticleRepository;
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
