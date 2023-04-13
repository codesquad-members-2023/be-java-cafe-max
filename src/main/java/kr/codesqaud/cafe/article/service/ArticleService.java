package kr.codesqaud.cafe.article.service;


import kr.codesqaud.cafe.article.domain.Article;
import kr.codesqaud.cafe.article.dto.ArticleDetailDto;
import kr.codesqaud.cafe.article.dto.ArticleFormDto;
import kr.codesqaud.cafe.article.dto.ArticlePreviewDto;
import kr.codesqaud.cafe.article.mapper.ArticleDtoMapper;
import kr.codesqaud.cafe.article.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;



    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    //글 저장
    public Long save(ArticleFormDto articleFormDto, String author) {
        return articleRepository.save(ArticleDtoMapper.INSTANCE.toArticle(articleFormDto, author));
    }

    //전체 글 목록을 DTO로 필터링 하고 반환
    public List<ArticlePreviewDto> getArticleListDtos() {
        List<Article> articles = articleRepository.findAll();
        List<ArticlePreviewDto> articleListDtos = new ArrayList<>();
        for (Article article : articles) {
            articleListDtos.add(ArticleDtoMapper.INSTANCE.toPreviewDto(article));
        }

        return articleListDtos;
    }


    //ID로 글을 찾아 DTO로 필터링 후 반환
    public ArticleDetailDto getArticleDetail(Long index) {
        Article article = articleRepository.findByID(index);
        return ArticleDtoMapper.INSTANCE.toDetailDto(article);
    }

    public void update(long id, ArticleFormDto articleFormDto) {
        articleRepository.modify(id, articleFormDto);
    }

    public void delete(long id) {
        articleRepository.delete(id);
    }
}
