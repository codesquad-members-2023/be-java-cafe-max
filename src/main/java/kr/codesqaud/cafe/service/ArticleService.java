package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.Repository.MemoryArticleRepository;
import kr.codesqaud.cafe.domain.Article;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    private final MemoryArticleRepository memoryArticleRepository;

    ArticleService(MemoryArticleRepository memoryArticleRepository) {
        this.memoryArticleRepository = new MemoryArticleRepository();
    }

    public Article saveArticle(final Article article) {
        memoryArticleRepository.saveArticle(article);
        return article;
    }

    public List<Article> findArticles() {
        return memoryArticleRepository.findAllArticle();
    }

    public Article findArticleBySequence(final int articleId) {
        Article article = memoryArticleRepository.findArticleById(articleId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 게시글이 없습니다."));
        return article;
    }
}



