package codesquad.cafe.article.service;

import codesquad.cafe.article.domain.Article;
import codesquad.cafe.article.dtio.ArticleRequestDto;
import codesquad.cafe.article.dtio.ArticleResponseDto;
import codesquad.cafe.article.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(final ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void createPost(final ArticleRequestDto articleRequestDto) {
        articleRepository.save(articleRequestDto.toEntity());
    }

    public List<ArticleResponseDto> findPosts() {
        List<Article> articles = articleRepository.findAll();
        List<ArticleResponseDto> articleResponseDtos = new ArrayList<>();
        for (Article article : articles) {
            articleResponseDtos.add(article.toDto());
        }
        return articleResponseDtos;
    }

    public ArticleResponseDto findPost(final Long id) {
        Article article = articleRepository.findById(id);
        return article.toDto();
    }
}
