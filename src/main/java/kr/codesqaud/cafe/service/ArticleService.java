package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.JdbcArticleRepository;
//import kr.codesqaud.cafe.repository.ArticleRepository;
//import kr.codesqaud.cafe.repository.BasicArticleRepository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ArticleService {
    private final JdbcArticleRepository articleRepository;
    public ArticleService(
//                    @Qualifier("JdbcArticleRepository")
                          JdbcArticleRepository articleRepository){
        this.articleRepository = articleRepository;
    }
    public void save(Article article){
        articleRepository.save(article);
    }
    public List<Article> getArticleList(){
        return articleRepository.getArticleList();
    }
    public Article getArticleById(long id){
        return articleRepository.getArticleById(id).get(); // 만약에 Optional 안에 Null 이라면...??  <<<<<<<< fix 필요 <<<<<
    }
}
