package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.dto.article.ArticleForm;
import kr.codesqaud.cafe.controller.dto.article.ArticleTimeForm;
import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.article.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ArticleService {
    // 서비스는 컨트롤러에서 DTO를 받아서 레포지토리에 domain으로 넘겨주는 역할
    // 위와 같이 이해했습니다.

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    // TODO: 매개변수가 DTO를 의존하도록 변경
    public Article add(ArticleForm form) {
        Article article = new Article(form.getWriter(), form.getTitle(), form.getContents());
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
