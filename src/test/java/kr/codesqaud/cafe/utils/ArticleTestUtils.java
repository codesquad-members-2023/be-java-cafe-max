package kr.codesqaud.cafe.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kr.codesqaud.cafe.article.domain.Article;
import kr.codesqaud.cafe.article.dto.ArticleListItem;
import kr.codesqaud.cafe.article.dto.ArticlePostRequest;
import kr.codesqaud.cafe.article.dto.ArticleResponse;
import kr.codesqaud.cafe.article.dto.ArticleTitleAndContentResponse;
import kr.codesqaud.cafe.article.dto.ArticleUpdateRequest;
import kr.codesqaud.cafe.mainPage.PaginationDto;

public class ArticleTestUtils {

	private static final Long ARTICLE_IDX = 1L;
	private static final String USER_ID = "testId";
	private static final String NICK_NAME = "tester";
	private static final String TITLE = "title";
	private static final String CONTENT = "content";
	private static final String DATE = "2023-4-27";

	public static Article createArticle() {
		return new Article(TITLE, CONTENT, USER_ID, NICK_NAME);
	}

	public static ArticlePostRequest createArticlePostRequest() {
		return new ArticlePostRequest(TITLE, CONTENT);
	}

	public static ArticleResponse createArticleResponse() {
		return new ArticleResponse(TITLE, CONTENT, ARTICLE_IDX, DATE, NICK_NAME);
	}

	public static ArticleUpdateRequest createArticleUpdateRequest() {
		return new ArticleUpdateRequest(TITLE, CONTENT);
	}

	public static ArticleTitleAndContentResponse createArticleTitleAndContentResponse() {
		return new ArticleTitleAndContentResponse(TITLE, CONTENT);
	}

	public static ArticleListItem createArticleResponseForList() {
		return new ArticleListItem(TITLE, ARTICLE_IDX, DATE, NICK_NAME);
	}

	public static PaginationDto createPaginationDto() {
		return new PaginationDto();
	}

	public static List<Article> createArticles() {
		return new ArrayList<>(Arrays.asList(createArticle()));
	}
}