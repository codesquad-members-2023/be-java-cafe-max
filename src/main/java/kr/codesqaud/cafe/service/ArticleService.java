package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.dto.ArticleDto;
import kr.codesqaud.cafe.controller.dto.request.PostEditRequest;
import kr.codesqaud.cafe.controller.dto.request.PostRequest;
import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void post(PostRequest postRequest, HttpServletRequest httpRequest) {
        Article article = Article.from(getUserIdFromSession(httpRequest), postRequest);
        articleRepository.save(article);
    }

    public List<ArticleDto> getArticles() {
        return articleRepository.findAll()
                .stream()
                .map(ArticleDto::from)
                .collect(Collectors.toUnmodifiableList());
    }

    public ArticleDto findById(final Long id) {
        return articleRepository.findById(id)
                .map(ArticleDto::from)
                .orElseThrow(() -> new RuntimeException("Article not found"));
    }

    public String getUserIdFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return (String) session.getAttribute("userId");
        }
        return null;
    }

    public void deleteArticle(Long articleId){
        articleRepository.delete(articleId);
    }

    public void editArticle(final Long articleId, final PostEditRequest request){
        Article savedArticle = articleRepository.findById(articleId).orElseThrow(() -> new RuntimeException("Article not found"));
        savedArticle.editArticle(request.getNewTitle(), request.getNewContent());
        articleRepository.update(savedArticle);
    }
}
