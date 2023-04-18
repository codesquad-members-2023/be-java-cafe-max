package kr.codesqaud.cafe.global.mapper;

import org.springframework.stereotype.Component;

import kr.codesqaud.cafe.article.domain.Article;
import kr.codesqaud.cafe.article.domain.Reply;
import kr.codesqaud.cafe.article.dto.ArticlePostRequest;
import kr.codesqaud.cafe.article.dto.ArticleResponse;
import kr.codesqaud.cafe.article.dto.ArticleTitleAndContentResponse;
import kr.codesqaud.cafe.article.dto.ArticleUpdateRequest;
import kr.codesqaud.cafe.article.dto.ReplyRequest;
import kr.codesqaud.cafe.article.dto.ReplyResponse;

@Component
public class ArticleMapper {
	public Article toArticle(ArticlePostRequest articlePostRequest) {
		return new Article(articlePostRequest.getTitle(), articlePostRequest.getContent(), articlePostRequest.getId(),
			articlePostRequest.getNickName());
	}

	public Article toArticle(ArticleUpdateRequest articleUpdateRequest) {
		return new Article(articleUpdateRequest.getTitle(), articleUpdateRequest.getContent(),
			articleUpdateRequest.getIdx());
	}

	public ArticleResponse toArticleResponse(Article article) {
		ArticleResponse articlePostRequest = new ArticleResponse(article.getTitle(), article.getContent(),
			article.getIdx(), article.getDate(), article.getNickName());
		return articlePostRequest;
	}

	public ArticleTitleAndContentResponse toArticleTitleAndContentResponse(Article article) {
		return new ArticleTitleAndContentResponse(article.getTitle(), article.getContent());
	}

	public Reply toReply(ReplyRequest replyRequest) {
		return new Reply(replyRequest.getId(), replyRequest.getArticleIdx(), replyRequest.getNickName(),
			replyRequest.getContent());
	}

	public ReplyResponse toReplyResponse(Reply reply) {
		return new ReplyResponse(reply.getNickName(), reply.getContent(), reply.getDate());
	}
}
