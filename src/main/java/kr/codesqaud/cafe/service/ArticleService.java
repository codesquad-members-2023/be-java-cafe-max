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

    public boolean writeArticle(ArticleFormDto dto){
        Article article = new Article.Builder()
                .title( dto.getTitle())
                .writer( dto.getWriter())
                .contents(dto.getContents())
                .build();
        articleRepository.save(article);
        return true;
    }

    public List<Article> getAricleList(){
        return articleRepository.findAll();
    }

    public Article findByIdx(int idx){
        return articleRepository.findByIdx(idx);
    }

}