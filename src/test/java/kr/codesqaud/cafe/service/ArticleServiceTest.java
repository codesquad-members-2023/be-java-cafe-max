package kr.codesqaud.cafe.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.BDDMockito.*;

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
	private ArticleResponse articleResponse;
	private ArticleTitleAndContentResponse articleTitleAndContentResponse;

	@BeforeEach
	void setUp() {
		article = new Article("제목입니다", "내용입니다", "id", "nickName");
		articleResponse = new ArticleResponse("title", "content", 1L, null, null);
		articleTitleAndContentResponse = new ArticleTitleAndContentResponse("title", "content");
	}

	@Test
	@DisplayName("articlePostRequest에 저장된 제목과 내용을 db에 저장한다.")
	void postTest() {
		//given
		ArticlePostRequest articlePostRequest = new ArticlePostRequest("제목입니다", "내용입니다");
		given(articleMapper.toArticle(articlePostRequest)).willReturn(article);

		//when
		articleService.post(articlePostRequest);

		//then
		verify(articleRepository, times(1)).save(article);
	}

	@Test
	@DisplayName("db에 저장된 모든 article을 역순으로 list에 담아 반환한다.")
	void getArticleListTest() {
		//given
		Article article = new Article("title1", "content1", 1L);
		Article article2 = new Article("title2", "content2", 2L);
		ArticleResponse articleResponse2 = new ArticleResponse("title2", "content2", 2L, "2023-4-18", "nickName");
		List<Article> articles = Arrays.asList(article, article2);
		given(articleRepository.findAll()).willReturn(articles);
		given(articleMapper.toArticleResponse(article)).willReturn(articleResponse);
		given(articleMapper.toArticleResponse(article2)).willReturn(articleResponse2);

		//when
		List<ArticleResponse> result = articleService.getArticleList();

		//then
		Assertions.assertAll(
			() -> assertThat(result.size() == 2).isTrue(),
			() -> assertThat(result.get(0)).isEqualTo(articleResponse2),
			() -> assertThat(result.get(1)).isEqualTo(articleResponse)
		);
	}

	@Test
	@DisplayName("idx를 통해 해당 article을 articleResponse의 형태로 반환한다.")
	void findArticleByIdx() {
		//given
		Long idx = 1L;
		Article article = new Article("title", "content", idx);

		given(articleRepository.findArticleByIdx(idx)).willReturn(Optional.of(article));
		given(articleMapper.toArticleResponse(article)).willReturn(articleResponse);

		//when
		ArticleResponse result = articleService.findArticleByIdx(idx);

		//then
		org.junit.jupiter.api.Assertions.assertAll(
			() -> assertThat(result).isEqualTo(articleResponse)
		);
	}

	@Test
	@DisplayName("ArticleUpdateRequest의 제목과 내용을 db에 업데이트 한다.")
	void updateArticle() {
		//given
		ArticleUpdateRequest articleUpdateRequest = new ArticleUpdateRequest("새로운제목", "새로운내용");
		Long idx = 1L;
		Article article = new Article("새로운제목", "새로운내용", idx);
		given(articleMapper.toArticle(articleUpdateRequest)).willReturn(article);

		//when
		articleService.updateArticle(articleUpdateRequest);

		//then
		verify(articleRepository, times(1)).updateArticle(article);
	}

	@Test
	@DisplayName("session의 id와 article의 id가 같다면 ArticleTitleAndContentResponse형태로 반환한다.")
	void validSessionIdAndArticleId() {
		Long idx = 1L;
		String id = "id";
		given(articleMapper.toArticleTitleAndContentResponse(article)).willReturn(articleTitleAndContentResponse);
		given(articleRepository.findArticleByIdx(idx)).willReturn(Optional.of(article));

		//when & then
		assertThatCode(() -> articleService.validSessionIdAndArticleId(idx, id)).doesNotThrowAnyException();
	}

	@Test
	@DisplayName("session id와 article의 id가 같다면 해당 article을 삭제한다.")
	void deleteArticleByIdx() {
		Long idx = 1L;
		String id = "id";
		given(articleRepository.findArticleByIdx(idx)).willReturn(Optional.of(article));
		given(articleMapper.toArticleTitleAndContentResponse(article)).willReturn(articleTitleAndContentResponse);

		//when
		articleService.deleteArticleByIdx(idx, id);

		//then
		verify(articleRepository, times(1)).deleteArticle(idx);
	}
}