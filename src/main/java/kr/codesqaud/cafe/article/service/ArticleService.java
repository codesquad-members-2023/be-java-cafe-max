package kr.codesqaud.cafe.article.service;


import kr.codesqaud.cafe.article.domain.Article;
import kr.codesqaud.cafe.article.dto.ArticleDetailDto;
import kr.codesqaud.cafe.article.dto.ArticleListDto;
import kr.codesqaud.cafe.article.mapper.ArticleDtoMapper;
import kr.codesqaud.cafe.article.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    private final ArticleDtoMapper articleDtoMapper;


    public ArticleService(ArticleRepository articleRepository, ArticleDtoMapper articleDtoMapper) {
        this.articleRepository = articleRepository;
        this.articleDtoMapper = articleDtoMapper;
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
            articleListDtos.add(ArticleDtoMapper.INSTANCE.toListDto(article));
        }

        return articleListDtos;
    }


    //ID로 글을 찾아 DTO로 필터링 후 반환
    public ArticleDetailDto getArticleDetail(Long index) {
        Article article = articleRepository.findByID(index);
        return ArticleDtoMapper.INSTANCE.toDetailDto(article);
    }
}
