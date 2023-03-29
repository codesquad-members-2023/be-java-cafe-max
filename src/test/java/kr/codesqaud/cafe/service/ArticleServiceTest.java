package kr.codesqaud.cafe.service;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import kr.codesqaud.cafe.controller.dto.req.PostingRequest;
import kr.codesqaud.cafe.exception.NotFoundException;
import kr.codesqaud.cafe.repository.impl.ArticleMemoryRepository;

class ArticleServiceTest {

	private ArticleService articleService;

	@BeforeEach
	void setArticleRepository() {
		articleService = new ArticleService(new ArticleMemoryRepository());
	}

	@DisplayName("게시글을 올릴 때")
	@Nested
	class PostingTest {

		@DisplayName("포스팅 정보가 주어지면 포스팅에 성공한다.")
		@Test
		void givenPostingRequest_whenPosting_thenReturnsNothing() {
			// given
			PostingRequest postingRequest = new PostingRequest("브루니", "게시글 제목", "게시글 내용");

			// when & then
			assertThatCode(() -> articleService.posting(postingRequest))
				.doesNotThrowAnyException();
		}
	}

	@DisplayName("게시글을 조회할 때")
	@Nested
	class ArticleFindTest {

		@DisplayName("모든 게시글을 조회하면 게시글 리스트를 반환한다.")
		@Test
		void givenNothing_whenFindAll_thenReturnsArticleList() {
			// given
			articleService.posting(new PostingRequest("브루니", "게시글 제목", "게시글 내용"));
			articleService.posting(new PostingRequest("브루니", "게시글 제목", "게시글 내용"));
			articleService.posting(new PostingRequest("브루니", "게시글 제목", "게시글 내용"));

			// when & then
			SoftAssertions.assertSoftly(softAssertions -> {
				softAssertions.assertThatCode(() -> articleService.getArticles())
					.doesNotThrowAnyException();
				softAssertions.assertThat(articleService.getArticles()).hasSize(3);
			});
		}

		@DisplayName("게시글 아이디가 주어지면 게시글을 반환한다.")
		@Test
		void givenArticleId_whenFindById_thenReturnsArticle() {
			// given
			Long articleId = 1L;
			articleService.posting(new PostingRequest("브루니", "게시글 제목", "게시글 내용"));

			// when & then

			assertThatCode(() -> articleService.findById(articleId))
				.doesNotThrowAnyException();
		}

		@DisplayName("존재하지 않는 게시글 아이디가 주어지면 예외를 던진다.")
		@Test
		void givenNotExistsArticleId_whenFindById_thenThrowsException() {
			// given
			Long articleId = 1L;

			// when & then
			assertThatThrownBy(() -> articleService.findById(articleId))
				.isInstanceOf(NotFoundException.class);
		}
	}
}