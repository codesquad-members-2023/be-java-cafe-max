package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.dto.article.ArticleResponse;
import kr.codesqaud.cafe.dto.article.ArticleSaveRequest;
import kr.codesqaud.cafe.exception.article.ArticleNotFoundException;
import kr.codesqaud.cafe.repository.article.ArticleRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(@Qualifier("jdbcArticleRepository") ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void saveArticle(ArticleSaveRequest articleSaveRequest) {
        articleRepository.save(articleSaveRequest.toArticle());
    }

    public List<ArticleResponse> getAllArticles() {
        return articleRepository.findAll().stream().map(ArticleResponse::from).collect(Collectors.toUnmodifiableList());
    }

    public ArticleResponse findById(Long id) {
        if (!articleRepository.exists(id)) {
            throw new ArticleNotFoundException();
        }

        return ArticleResponse.from(articleRepository.findById(id));
    }
}
