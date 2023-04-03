package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.dto.ArticleCreateDto;
import kr.codesqaud.cafe.repository.ArticleRepository;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }


    public Long create(ArticleCreateDto articleCreateDto) {
        return articleRepository.save(articleCreateDto.toArticle());
    }
}
