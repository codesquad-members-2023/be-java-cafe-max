package codesquad.cafe.article.service;

import codesquad.cafe.article.dto.ArticleResponseDto;
import codesquad.cafe.article.dto.ArticleUpdateRequestDto;
import codesquad.cafe.article.repository.ArticleRepository;
import codesquad.cafe.article.domain.Article;
import codesquad.cafe.article.dto.ArticleRequestDto;
import codesquad.cafe.global.util.Criteria;
import codesquad.cafe.reply.domain.Reply;
import codesquad.cafe.reply.repository.ReplyRepository;
import codesquad.cafe.user.domain.User;
import codesquad.cafe.global.exception.CustomException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static codesquad.cafe.global.exception.ErrorCode.NOT_MATCH_ARTICLE_USER_AND_REPLY_USER;
import static codesquad.cafe.global.exception.ErrorCode.NOT_MATCH_USER_AND_ARTICLE;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ReplyRepository replyRepository;

    public ArticleService(final ArticleRepository articleRepository, final ReplyRepository replyRepository) {
        this.articleRepository = articleRepository;
        this.replyRepository = replyRepository;
    }

    public void createPost(final ArticleRequestDto articleRequestDto, final User user) {
        articleRepository.save(articleRequestDto.toEntity(user.getId()));
    }

    public List<ArticleResponseDto> getPagingArticles(final Criteria criteria) {
        List<Article> articles = articleRepository.findPagingArticles(criteria);
        List<ArticleResponseDto> articleResponseDtos = new ArrayList<>();
        for (Article article : articles) {
            articleResponseDtos.add(new ArticleResponseDto(article, findWriterName(article)));
        }
        return articleResponseDtos;
    }

    public ArticleResponseDto findPost(final Long id) {
        Article article = articleRepository.findById(id);
        return new ArticleResponseDto(article, findWriterName(article));
    }

    private String findWriterName(final Article article) {
        return articleRepository.findWriterByUserId(article);
    }

    public void updatePost(final ArticleUpdateRequestDto articleUpdateRequestDto, final Long postId, final User user) {
        Article article = articleRepository.findById(postId);
        validateWriter(user, article);
        articleRepository.update(article.update(articleUpdateRequestDto.getTitle(), articleUpdateRequestDto.getContents()));
    }

    private void validateWriter(final User user, final Article article) {
        if (!article.getWriterId().equals(user.getId())) {
            throw new CustomException(NOT_MATCH_USER_AND_ARTICLE);
        }
    }

    public void deletePost(final Long postId, final User user) {
        Article article = articleRepository.findById(postId);
        validateWriter(user, article);
        List<Reply> replies = replyRepository.findAllByPost(postId);
        if (validateReplyExistence(replies)) {
            validateReplyAndPost(replies, user);
        }
        replyRepository.deleteAllByPostId(postId);
        articleRepository.deletePostById(postId);
    }

    private void validateReplyAndPost(final List<Reply> replies, final User user) {
        for (Reply reply : replies) {
            if (!reply.getUserId().equals(user.getId())) {
                throw new CustomException(NOT_MATCH_ARTICLE_USER_AND_REPLY_USER);
            }
        }
    }

    private boolean validateReplyExistence(final List<Reply> replies) {
        if (replies.isEmpty()) {
            return false;
        }
        return true;
    }

    public ArticleResponseDto findPostByIdAndUser(final Long postId, final User user) {
        Article article = articleRepository.findById(postId);
        validateWriter(user, article);
        return new ArticleResponseDto(article, findWriterName(article));
    }

    public int getTotal() {
        return articleRepository.getTotal();
    }
}
