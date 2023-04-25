package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.BasicArticleRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ArticleService {
    private final BasicArticleRepository articleRepository;
    public ArticleService(BasicArticleRepository articleRepository){
        this.articleRepository = articleRepository;
    }
    public void save(Article article){
        articleRepository.save(article);
    }
    public List<Article> getArticleList(){
        return articleRepository.getArticleList();
    }

    public Article getArticleByArticleNum(long articleNum){
        return articleRepository.getArticleByArticleNum(articleNum).get(); // 만약에 Optional 안에 Null 이라면...??  <<<<<<<< fix 필요 <<<<<
    }
}
