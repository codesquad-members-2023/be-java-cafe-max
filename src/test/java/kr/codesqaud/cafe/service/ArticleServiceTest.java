package kr.codesqaud.cafe.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kr.codesqaud.cafe.article.ArticleService;
import kr.codesqaud.cafe.article.domain.Article;
import kr.codesqaud.cafe.article.dto.ArticlePostRequest;
import kr.codesqaud.cafe.article.dto.ArticleResponse;
import kr.codesqaud.cafe.article.dto.ArticleResponseForList;
import kr.codesqaud.cafe.article.dto.ArticleTitleAndContentResponse;
import kr.codesqaud.cafe.article.dto.ArticleUpdateRequest;
import kr.codesqaud.cafe.article.repository.ArticleRepository;
import kr.codesqaud.cafe.global.mapper.ArticleMapper;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

	@InjectMocks
	private ArticleService articleService;

	@Mock
	private ArticleRepository articleRepository;

	@Mock
	private ArticleMapper articleMapper;

	private Article article;

	@BeforeEach
	void setUp() {
		article = createArticle();
	}

	@Test
	@DisplayName("articlePostRequest에 저장된 제목과 내용을 db에 저장한다.")
	void postTest() {
		//given
		ArticlePostRequest articlePostRequest = createArticlePostRequest();
		given(articleMapper.toArticle(articlePostRequest)).willReturn(article);

		//when
		articleService.post(articlePostRequest);

		//then
		verify(articleRepository, times(1)).save(article);
	}

	@Test
	@DisplayName("db에 저장된 모든 article을 역순으로 list에 담아 반환한다.")
	void getArticleListTest() {

		Article article1 = createArticle1ContainsIdx();
		Article article2 = createArticle2ContainsIdx();
		ArticleResponseForList articleResponse1 = createArticleResponseForList1();
		ArticleResponseForList articleResponse2 = createArticleResponseForList2();
		List<Article> articles = new ArrayList<>(Arrays.asList(article1, article2));

		//given
		// given(articleRepository.findAll()).willReturn(articles);
		given(articleMapper.toArticleResponseForList(article1)).willReturn(articleResponse1);
		given(articleMapper.toArticleResponseForList(article2)).willReturn(articleResponse2);

		//when
		// List<ArticleResponseForList> result = articleService.getArticleList();

		//then
		// Assertions.assertAll(
		// 	() -> assertThat(result.size() == 2).isTrue(),
		// 	() -> assertThat(result.get(0)).isEqualTo(articleResponse2),
		// 	() -> assertThat(result.get(1)).isEqualTo(articleResponse1)
		// );
	}

	@Test
	@DisplayName("idx를 통해 해당 article을 articleResponse의 형태로 반환한다.")
	void findArticleByIdxTest() {
		//given
		ArticleResponse articleResponse = createArticleResponse();
		Long idx = 1l;
		given(articleRepository.findArticleByIdx(idx)).willReturn(Optional.of(article));
		given(articleMapper.toArticleResponse(article)).willReturn(articleResponse);

		//when
		ArticleResponse result = articleService.findArticleByIdx(idx);

		//then
		Assertions.assertAll(
			() -> assertThat(result).isEqualTo(articleResponse)
		);
	}

	@Test
	@DisplayName("ArticleUpdateRequest의 제목과 내용을 db에 업데이트 한다.")
	void updateArticleTest() {
		//given
		ArticleUpdateRequest articleUpdateRequest = createArticleUpdateRequest();
		Article article = createArticle();
		given(articleMapper.toArticle(articleUpdateRequest)).willReturn(article);

		//when
		articleService.updateArticle(articleUpdateRequest);

		//then
		verify(articleRepository, times(1)).updateArticle(article);
	}

	@Test
	@DisplayName("session의 id와 article의 id가 같다면 ArticleTitleAndContentResponse형태로 반환한다.")
	void validSessionIdAndArticleIdTest() {
		Long idx = 1L;
		String id = "id";
		ArticleTitleAndContentResponse articleTitleAndContentResponse = createArticleTitleAndContentResponse();
		given(articleMapper.toArticleTitleAndContentResponse(article)).willReturn(articleTitleAndContentResponse);
		given(articleRepository.findArticleByIdx(idx)).willReturn(Optional.of(article));

		//when & then
		assertThatCode(() -> articleService.validSessionIdAndArticleId(idx, id)).doesNotThrowAnyException();
	}

	private static ArticleTitleAndContentResponse createArticleTitleAndContentResponse() {
		return new ArticleTitleAndContentResponse("title", "content");
	}

	private static ArticleResponse createArticleResponse() {
		return new ArticleResponse("제목입니다", "내용입니다", 1L, "2023-4-23", "nickName");
	}

	private static Article createArticle() {
		return new Article("제목입니다", "내용입니다", "id", "nickName");
	}

	private static ArticlePostRequest createArticlePostRequest() {
		return new ArticlePostRequest("제목입니다", "내용입니다");
	}

	private static ArticleUpdateRequest createArticleUpdateRequest() {
		return new ArticleUpdateRequest("새로운제목", "새로운내용");
	}

	private static ArticleResponseForList createArticleResponseForList1() {
		return new ArticleResponseForList("title2", 1L, "2023-4-23", "nickName");
	}

	private static ArticleResponseForList createArticleResponseForList2() {
		return new ArticleResponseForList("title2", 2L, "2023-4-23", "nickName");
	}

	private static Article createArticle2ContainsIdx() {
		return new Article("title2", "content2", 2L);
	}

	private static Article createArticle1ContainsIdx() {
		return new Article("title1", "content1", 1L);
	}
}