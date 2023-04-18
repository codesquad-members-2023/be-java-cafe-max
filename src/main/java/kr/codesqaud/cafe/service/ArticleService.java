package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.dto.ArticleForm;
import kr.codesqaud.cafe.repository.ArticleRepository;
import kr.codesqaud.cafe.repository.MemoryArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(MemoryArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
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

    public Article findById(long id) {
        return articleRepository.findById(id);
    }
}
