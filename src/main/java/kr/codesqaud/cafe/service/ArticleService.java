package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void save(Article article) {
        articleRepository.save(article);
    }

    public List<Article> getArticleList() {
        return articleRepository.getArticleList();
    }

    public Article getArticleById(long id) {
        return articleRepository.getArticleById(id).get(); // 만약에 Optional 안에 Null 이라면...??  <<<<<<<< fix 필요 <<<<<
    }
}
