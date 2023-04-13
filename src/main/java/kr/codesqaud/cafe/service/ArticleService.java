package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.dto.article.ArticleResponse;
import kr.codesqaud.cafe.dto.article.ArticleSaveRequest;
import kr.codesqaud.cafe.dto.article.ArticleUpdateRequest;
import kr.codesqaud.cafe.dto.article.ArticleWithSurroundingResponse;
import kr.codesqaud.cafe.repository.article.ArticleRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(@Qualifier("jdbcArticleRepository") ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Long saveArticle(ArticleSaveRequest articleSaveRequest) {
        return articleRepository.save(articleSaveRequest.toArticle());
    }

    public List<ArticleResponse> getAllArticles() {
        return articleRepository.findAll().stream().map(ArticleResponse::from).collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public ArticleResponse findById(Long id) {
        return ArticleResponse.from(articleRepository.findById(id));
    }

    @Transactional
    public ArticleWithSurroundingResponse getArticleWithSurrounding(Long id) {
        return ArticleWithSurroundingResponse.from(articleRepository.findWithSurroundingArticles(id));
    }

    public int editArticle(ArticleUpdateRequest articleUpdateRequest) {
        return articleRepository.edit(articleUpdateRequest.toArticle());
    }
}
