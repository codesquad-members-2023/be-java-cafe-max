package kr.codesqaud.cafe.global.mapper;

import kr.codesqaud.cafe.article.domain.Article;
import kr.codesqaud.cafe.article.dto.ArticlePostRequest;
import kr.codesqaud.cafe.article.dto.ArticleResponse;
import kr.codesqaud.cafe.article.dto.ArticleTitleAndContentResponse;
import kr.codesqaud.cafe.article.dto.ArticleUpdateRequest;

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
		// ArticleResponse.setNickName(article.getNickName());
		return articlePostRequest;
	}

	public ArticleTitleAndContentResponse toArticleTitleAndContentResponse(Article article) {
		return new ArticleTitleAndContentResponse(article.getTitle(), article.getContent());
	}
}
