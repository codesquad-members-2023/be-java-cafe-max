package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.Repository.ArticleRepository;
import kr.codesqaud.cafe.Repository.MemoryArticleRepository;
import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.dto.ArticleRequestDto;
import kr.codesqaud.cafe.dto.ArticleResponseDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    ArticleService(final ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void saveArticle(final ArticleRequestDto articleRequestDto) {
        articleRepository.saveArticle(articleRequestDto.toEntity());
    }

    public List<ArticleResponseDto> findArticles() {
        List<ArticleResponseDto> articleResponseDtos = new ArrayList<>();

        List<Article> articles = articleRepository.findAllArticle();
        for(Article article: articles){
            articleResponseDtos.add(article.toDto());
        }
        return articleResponseDtos;
    }

    public ArticleResponseDto findArticleBySequence(final int articleId) {
        Article article = articleRepository.findArticleById(articleId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 게시글이 없습니다."));
        ArticleResponseDto articleResponseDto = article.toDto();
        return articleResponseDto;
    }
}



