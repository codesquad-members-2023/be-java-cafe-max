package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.dto.ArticleDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArticleRepository {
    private List<Article> articleRepository;

    public ArticleRepository() {
        this.articleRepository = new ArrayList<>();
    }

    public void save(ArticleDto articleDto){
        articleRepository.add(articleDto.toArticle());
    }

    public List<ArticleDto> findAll(){
        return articleRepository.stream()
                .map(article -> new ArticleDto(article.getTitle(),article.getContent()))
                .collect(Collectors.toList());
    }
}
