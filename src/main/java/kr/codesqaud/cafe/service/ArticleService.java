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

    public Article getArticleByArticleNum(Long articleNum){
        return articleRepository.getArticleByArticleNum(articleNum).get(); // Optional 때문에 끝에 .get()추가 -> 'Optional 안의 값 반환'이란 뜻(인 듯..)
    }
}
