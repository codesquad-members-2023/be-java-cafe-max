package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.dto.ArticleDto;
import kr.codesqaud.cafe.repository.ArticleRepository;

public class ArticleService {
    private ArticleRepository articleRepository;

    public ArticleService() {
        this.articleRepository = new ArticleRepository();
    }

    public void post(ArticleDto articleDto){
        articleRepository.save(articleDto);
    }

}
