package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.dto.ArticleDto;
import kr.codesqaud.cafe.controller.dto.request.PostRequest;
import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void post(PostRequest request) {
        Article article = Article.from(request);
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
}
