package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.ArticleRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    public ArticleService(ArticleRepository articleRepository){
        this.articleRepository = articleRepository;
    }
    public void join(Article article){
        articleRepository.save(article);
    }
    public List<Article> getArticleList(){
        return articleRepository.getArticleList();
    }
}
