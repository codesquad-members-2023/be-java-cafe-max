package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.controller.dto.ArticleDTO;
import kr.codesqaud.cafe.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    private ArticleRepository articleRepository;

    public ArticleService() {
        this.articleRepository = new ArticleRepository();
    }

    public void post(ArticleDTO articleDto){
        articleRepository.save(articleDto);
    }

    public List<Article> getArticleList(){
        return articleRepository.findAll();
    }

    public ArticleDTO findArticleById(int id){
        return articleRepository.findArticle(id);
    }
}
