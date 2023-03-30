package kr.codesqaud.cafe.service;

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

    public List<ArticleDto> getArticleList(){
        return articleRepository.findAll();
    }

}
