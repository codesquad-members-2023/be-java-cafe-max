package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.entity.User;
import kr.codesqaud.cafe.dto.ArticlePostRequest;
import kr.codesqaud.cafe.domain.entity.Article;
import kr.codesqaud.cafe.dto.ArticleResponse;
import kr.codesqaud.cafe.dto.UserResponse;
import kr.codesqaud.cafe.repository.ArticleH2Repository;
import kr.codesqaud.cafe.repository.ArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleH2Repository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<ArticleResponse> findArticles() {
        return articleRepository.findAll()
                .stream()
                //TODO Entity -> Dto 메서드
                .map(article -> new ArticleResponse(
                        article.getArticleId(),
                        article.getWriter(),
                        article.getTitle(),
                        article.getContents()
                ))
                .collect(Collectors.toList());

    }

    public ArticleResponse findByArticleId(long articleId) {
        Optional<Article> targetArticle = articleRepository.findByArticleId(articleId);

        //TODO Entity -> Dto 메서드
        return new ArticleResponse(
                targetArticle.get().getArticleId(),
                targetArticle.get().getWriter(),
                targetArticle.get().getTitle(),
                targetArticle.get().getContents()
        );
    }

    public void post(ArticlePostRequest articlePostRequest) {
        Article article = new Article(
                articlePostRequest.getWriter(),
                articlePostRequest.getTitle(),
                articlePostRequest.getContents()
        );

        articleRepository.save(article);
    }

}

