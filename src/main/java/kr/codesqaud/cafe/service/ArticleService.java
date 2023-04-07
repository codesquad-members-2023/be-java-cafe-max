package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.article.Article;
import kr.codesqaud.cafe.domain.article.repository.ArticleRepository;
import kr.codesqaud.cafe.dto.ArticleFormDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void writeArticle(ArticleFormDto dto){
        Article article = new Article(dto.getWriter(), dto.getTitle(), dto.getContents());
        articleRepository.save(article);
    }

    public List<Article> getAricleList(){
        return articleRepository.findAll();
    }

    public Article findByIDX(int idx){
        return articleRepository.findByIDX(idx);
    }

}
