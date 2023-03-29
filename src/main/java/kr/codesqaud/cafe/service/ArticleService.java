package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.question.ArticleRepository;
import kr.codesqaud.cafe.dto.ArticleDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void questionWrite(ArticleDto articleDto){
        articleRepository.save(articleDto);
    }
    public List<ArticleDto> getArticleList(){
        return articleRepository.findAll();
    }
}
