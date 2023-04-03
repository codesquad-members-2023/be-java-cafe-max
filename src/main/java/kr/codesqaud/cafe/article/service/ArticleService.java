package kr.codesqaud.cafe.article.service;


import kr.codesqaud.cafe.article.domain.Article;
import kr.codesqaud.cafe.article.dto.ArticleDetailDto;
import kr.codesqaud.cafe.article.dto.ArticleListDto;
import kr.codesqaud.cafe.article.repository.ArticleRepository;

import java.util.ArrayList;
import java.util.List;


public class ArticleService {

    private final ArticleRepository articleRepository;


    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    //글 저장
    public Long save(Article article) {
        return articleRepository.save(article);
    }

    //전체 글 목록을 DTO로 필터링 하고 반환
    public List<ArticleListDto> getArticleListDtos() {
        List<Article> articles = articleRepository.findAll();
        List<ArticleListDto> articleListDtos = new ArrayList<>();
        for (Article article : articles) {
            articleListDtos.add(new ArticleListDto(article.getAuthor(), article.getTitle(), article.getTime(), article.getId()));
        }

        return articleListDtos;
    }


    //ID로 글을 찾아 DTO로 필터링 후 반환
    public ArticleDetailDto getArticleDetail(Long index) {
        Article article = articleRepository.findByID(index);
        return new ArticleDetailDto(article.getAuthor(), article.getTitle(), article.getContents());
    }
}
