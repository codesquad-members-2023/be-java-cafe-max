package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.dto.ArticleForm;
import kr.codesqaud.cafe.repository.ArticleRepository;
import kr.codesqaud.cafe.repository.JdbcArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    private ArticleRepository articleRepository;

    public ArticleService(JdbcArticleRepository jdbcArticleRepository) {
        this.articleRepository = jdbcArticleRepository;
    }

    public void saveArticle(ArticleForm articleForm) {
        Article article = Article.builder()
                .writer((articleForm.getWriter()))
                .title(articleForm.getTitle())
                .contents(articleForm.getContents())
                .build();
        articleRepository.save(article);
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public Article findArticleIndexOf(int index) {
        return articleRepository.findById(index);
    }
}
