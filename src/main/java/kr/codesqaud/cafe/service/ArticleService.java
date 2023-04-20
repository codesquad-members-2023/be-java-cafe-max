package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.dto.article.ArticleResponse;
import kr.codesqaud.cafe.dto.article.ArticleSaveRequest;
import kr.codesqaud.cafe.dto.article.ArticleUpdateRequest;
import kr.codesqaud.cafe.dto.article.ArticleWithSurroundingResponse;
import kr.codesqaud.cafe.exception.article.ArticleNotFoundException;
import kr.codesqaud.cafe.repository.article.ArticleRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(@Qualifier("mySqlArticleRepository") ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public long saveArticle(ArticleSaveRequest articleSaveRequest) {
        return articleRepository.save(articleSaveRequest.toArticle());
    }

    public List<ArticleResponse> getAllArticles() {
        final List<Article> articles = articleRepository.findAll();
        Collections.reverse(articles);
        return articles.stream().map(ArticleResponse::from).collect(Collectors.toUnmodifiableList());
    }

    public ArticleResponse findById(Long id) {
        return ArticleResponse.from(articleRepository.findById(id).orElseThrow(ArticleNotFoundException::new));
    }

    public ArticleWithSurroundingResponse getArticleWithSurrounding(Long id) {
        return ArticleWithSurroundingResponse.from(articleRepository.findWithSurroundingArticles(id).orElseThrow(ArticleNotFoundException::new));
    }

    public int editArticle(ArticleUpdateRequest articleUpdateRequest) {
        return articleRepository.edit(articleUpdateRequest.toArticle());
    }

    public int deleteArticle(final Long id) {
        return articleRepository.delete(id);
    }
}
