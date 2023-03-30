package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.dto.ArticleDto;
import kr.codesqaud.cafe.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    private ArticleRepository articleRepository;

    public ArticleService() {
        this.articleRepository = new ArticleRepository();
    }

    public void post(ArticleDto articleDto){
        articleRepository.save(articleDto);
    }

    public List<Article> getArticleList(){
        return articleRepository.findAll();
    }

    public ArticleDto findArticleById(int id){
        return articleRepository.findArticle(id);
    }
}
