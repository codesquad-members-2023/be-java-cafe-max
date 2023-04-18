package codesquad.cafe.domain.article.service;

import codesquad.cafe.domain.article.domain.Article;
import codesquad.cafe.domain.article.dto.ArticleRequestDto;
import codesquad.cafe.domain.article.dto.ArticleResponseDto;
import codesquad.cafe.domain.article.dto.ArticleUpdateRequestDto;
import codesquad.cafe.domain.article.repository.ArticleRepository;
import codesquad.cafe.domain.user.domain.User;
import codesquad.cafe.global.exception.CustomException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static codesquad.cafe.global.exception.ErrorCode.NOT_MATCH_USER_AND_WRITER;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(final ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void createPost(final ArticleRequestDto articleRequestDto, final User user) {
        articleRepository.save(articleRequestDto.toEntity(), user.getId());
    }

    public List<ArticleResponseDto> findPosts() {
        List<Article> articles = articleRepository.findAll();
        List<ArticleResponseDto> articleResponseDtos = new ArrayList<>();
        for (Article article : articles) {
            articleResponseDtos.add(article.toDto(findWriterName(article)));
        }
        return articleResponseDtos;
    }

    public ArticleResponseDto findPost(final Long id) {
        Article article = articleRepository.findById(id);
        return article.toDto(findWriterName(article));
    }

    private String findWriterName(final Article article) {
        return articleRepository.findWriterByUserId(article.getWriterId());
    }

    public void updatePost(final ArticleUpdateRequestDto articleUpdateRequestDto, final Long postId, final User user) {
        Article article = articleRepository.findById(postId);
        validateWriter(user, article);
        articleRepository.update(article.update(articleUpdateRequestDto));
    }

    private void validateWriter(final User user, final Article article) {
        if (!article.getWriterId().equals(user.getId())) {
            throw new CustomException(NOT_MATCH_USER_AND_WRITER);
        }
    }

    public void deletePost(final Long postId, final User user) {
        Article article = articleRepository.findById(postId);
        validateWriter(user, article);
        articleRepository.deletePostById(postId);
    }

    public ArticleResponseDto findPostByIdAndUser(final Long postId, final User user) {
        Article article = articleRepository.findById(postId);
        validateWriter(user, article);
        return article.toDto(findWriterName(article));
    }
}
