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
import kr.codesqaud.cafe.article.exception.ArticleDeleteException;
import kr.codesqaud.cafe.article.exception.ArticleIdAndSessionIdMismatchException;
import kr.codesqaud.cafe.article.repository.ArticleRepository;
import kr.codesqaud.cafe.global.mapper.ArticleMapper;
import kr.codesqaud.cafe.mainPage.PaginationDto;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

	@InjectMocks
	private ArticleService articleService;

	@Mock
	private ArticleRepository articleRepository;

	@Mock
	private ArticleMapper articleMapper;

	private Article article;

	private ArticleResponse articleResponse;

	private ArticlePostRequest articlePostRequest;

	private ArticleUpdateRequest articleUpdateRequest;

	private ArticleTitleAndContentResponse articleTitleAndContentResponse;

	private static final Long articleIdx = 1L;

	private static final String userId = "id";
	private static final String nickName = "tester";
	private static final String title = "title";
	private static final String content = "content";
	private static final String date = "2023-4-27";

	@BeforeEach
	void setUp() {
		article = new Article(title, content, userId, nickName);
		articlePostRequest = new ArticlePostRequest(title, content);
		articleResponse = new ArticleResponse(title, content, articleIdx, date, nickName);
		articleUpdateRequest = new ArticleUpdateRequest(title, content);
		articleTitleAndContentResponse = new ArticleTitleAndContentResponse(title, content);
	}

	@Test
	@DisplayName("articlePostRequest에 저장된 제목과 내용을 db에 저장한다.")
	void postTest() {
		//given
		given(articleMapper.toArticle(articlePostRequest)).willReturn(article);

		//when
		articleService.post(articlePostRequest);

		//then
		verify(articleRepository, times(1)).save(article);
	}

	@Test
	@DisplayName("db에 저장된 모든 article을 역순으로 list에 담아 반환한다.")
	void getArticleListTest() {
		PaginationDto paginationDto = new PaginationDto();
		ArticleResponseForList articleResponseForList = new ArticleResponseForList(title, articleIdx, date, nickName);
		List<Article> articles = new ArrayList<>(Arrays.asList(article));

		//given
		given(articleRepository.findAll(any(PaginationDto.class))).willReturn(articles);
		given(articleMapper.toArticleResponseForList(article)).willReturn(articleResponseForList);

		//when
		List<ArticleResponseForList> result = articleService.getArticleList(paginationDto);

		//then
		Assertions.assertAll(
			() -> assertThat(result.size() == 1).isTrue(),
			() -> assertThat(result.get(0)).isEqualTo(articleResponseForList)
		);
	}

	@Test
	@DisplayName("idx를 통해 해당 article을 articleResponse의 형태로 반환한다.")
	void findArticleByIdxTest() {
		//given
		given(articleRepository.findArticleByIdx(articleIdx)).willReturn(Optional.of(article));
		given(articleMapper.toArticleResponse(article)).willReturn(articleResponse);

		//when
		ArticleResponse result = articleService.findArticleByIdx(articleIdx);

		//then
		Assertions.assertAll(
			() -> assertThat(result).isEqualTo(articleResponse)
		);
	}

	@Test
	@DisplayName("ArticleUpdateRequest의 제목과 내용을 db에 업데이트 한다.")
	void updateArticleTest() {
		//given
		given(articleMapper.toArticle(articleUpdateRequest)).willReturn(article);

		//when
		articleService.updateArticle(articleUpdateRequest);

		//then
		verify(articleRepository, times(1)).updateArticle(article);
	}

	@Test
	@DisplayName("session의 id와 article의 id가 같다면 ArticleTitleAndContentResponse를 반환한다.")
	void validSessionIdAndArticleIdTest() {
		//given
		given(articleMapper.toArticleTitleAndContentResponse(article)).willReturn(articleTitleAndContentResponse);
		given(articleRepository.findArticleByIdx(articleIdx)).willReturn(Optional.of(article));

		//when & then
		assertThatCode(() -> articleService.validSessionIdAndArticleId(articleIdx, userId)).doesNotThrowAnyException();
	}

	@Test
	@DisplayName("session의 id와 article의 id가 다르다면 ArticleIdAndSessionIdMismatchException이 발생한다.")
	void validSessionIdAndArticleIdTest_throwsException() {
		//given
		given(articleRepository.findArticleByIdx(articleIdx)).willReturn(Optional.of(article));

		//when & then
		assertThatThrownBy(
			() -> articleService.validSessionIdAndArticleId(articleIdx, userId + "different")).isInstanceOf(
			ArticleIdAndSessionIdMismatchException.class);
	}

	@Test
	@DisplayName("session의 id와 article의  id가 같다면 해당 게시글을 삭제할수 있다.")
	void deleteArticleByIdxTest() {
		//given
		given(articleRepository.findArticleByIdx(articleIdx)).willReturn(Optional.of(article));
		given(articleMapper.toArticleTitleAndContentResponse(article)).willReturn(
			articleTitleAndContentResponse);
		given(articleRepository.deleteArticle(articleIdx, userId)).willReturn(true);

		//when & then
		assertThatCode(() -> articleService.deleteArticleByIdx(articleIdx, userId)).doesNotThrowAnyException();
	}

	@Test
	@DisplayName("session의 id와 article의  id가 같아도 해당 게시글에 다른 사용자의 댓글이 있다면 삭제할수 없다.")
	void deleteArticleByIdx_throwsException() {
		//given
		given(articleRepository.findArticleByIdx(articleIdx)).willReturn(Optional.of(article));
		given(articleMapper.toArticleTitleAndContentResponse(article)).willReturn(
			articleTitleAndContentResponse);
		given(articleRepository.deleteArticle(articleIdx, userId)).willReturn(false);

		//when & then
		assertThatThrownBy(() -> articleService.deleteArticleByIdx(articleIdx, userId)).isInstanceOf(
			ArticleDeleteException.class);
	}

}