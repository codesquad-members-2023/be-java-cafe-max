package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.dto.ArticleDto;

import java.util.ArrayList;
import java.util.List;

public class ArticleRepository {
    private List<Article> articleRepository;

    public ArticleRepository() {
        this.articleRepository = new ArrayList<>();
    }

    public void save(ArticleDto articleDto){
        articleRepository.add(articleDto.toArticle());
    }
}
