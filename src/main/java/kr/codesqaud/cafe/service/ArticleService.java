package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.SessionConst;
import kr.codesqaud.cafe.controller.dto.article.ArticleForm;
import kr.codesqaud.cafe.controller.dto.article.ArticleTimeForm;
import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.article.ArticleRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article add(ArticleForm form, HttpSession session) {
        Article article = new Article((String) session.getAttribute(SessionConst.LOGIN_NAME), form.getTitle(), form.getContents());
        return articleRepository.save(article);
    }

    public List<ArticleTimeForm> findArticles() {
        // DTO가 가지고 있는 변수로 필터링하는 과정
        List<Article> articles = articleRepository.findAll();
        List<ArticleTimeForm> articleTimeForms = new ArrayList<>();
        for (Article article : articles) {
            articleTimeForms.add(ArticleTimeForm.from(article));
        }
        return articleTimeForms;
    }

    public ArticleTimeForm findArticleId(Long id) {
        // Optional을 스트림으로 처리
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("이 아이디를 찾을 수 없어: " + id));
        return ArticleTimeForm.from(article);
    }
}
