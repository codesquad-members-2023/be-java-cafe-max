package kr.codesqaud.cafe.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import kr.codesqaud.cafe.controller.dto.ArticleRequest;
import kr.codesqaud.cafe.controller.dto.req.ArticleEditRequest;
import kr.codesqaud.cafe.controller.dto.req.PostingRequest;
import kr.codesqaud.cafe.domain.article.Article;
import kr.codesqaud.cafe.exception.NoAuthorizationException;
import kr.codesqaud.cafe.exception.NotFoundException;
import kr.codesqaud.cafe.repository.ArticleRepository;
import kr.codesqaud.cafe.repository.impl.ArticleMemoryRepository;

class ArticleServiceTest {

	private ArticleService articleService;
	private ArticleRepository articleRepository;

	@BeforeEach
	void setArticleRepository() {
		articleRepository = new ArticleMemoryRepository();
		articleService = new ArticleService(articleRepository);
	}

	@DisplayName("게시글을 올릴 때")
	@Nested
	class PostingTest {

		@DisplayName("포스팅 정보가 주어지면 포스팅에 성공한다.")
		@Test
		void givenPostingRequest_whenPosting_thenReturnsNothing() {
			// given
			PostingRequest request = new PostingRequest("게시글 제목", "게시글 내용");

			// when & then
			assertThatCode(() -> articleService.post(request, "브루니"))
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
			articleRepository.save(new Article(null, "브루니", "게시글 제목", "게시글 내용", LocalDateTime.now()));
			articleRepository.save(new Article(null, "브루니", "게시글 제목", "게시글 내용", LocalDateTime.now()));
			articleRepository.save(new Article(null, "브루니", "게시글 제목", "게시글 내용", LocalDateTime.now()));

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
			articleRepository.save(new Article(null, "bruni", "제목", "내용", LocalDateTime.now()));

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

	@DisplayName("게시글의 작성자와 로그인한 사용자가 일치하면 검증에 성공한다.")
	@Test
	void givenSameArticleWriterAndUserId_whenValidateHasAuthorization_thenDoNothing() {
		// given
		articleRepository.save(new Article(null, "bruni", "제목", "내용", LocalDateTime.now()));
		String userId = "bruni";

		// when & then
		assertThatCode(() -> articleService.validateHasAuthorization(1L, userId))
			.doesNotThrowAnyException();
	}

	@DisplayName("게시글의 작성자와 로그인한 사용자가 일치하지 않으면 예외를 던진다.")
	@Test
	void givenNotEqualArticleWriterAndUserId_whenValidateHasAuthorization_throwsException() {
		// given
		articleRepository.save(new Article(null, "bruni", "게시글 제목", "게시글 내용", LocalDateTime.now()));
		String userId = "unknown";

		// when & then
		assertThatThrownBy(() -> articleService.validateHasAuthorization(1L, userId))
			.isInstanceOf(NoAuthorizationException.class);
	}

	@DisplayName("게시글을 수정할 때")
	@Nested
	class EditArticleTest {

		@DisplayName("게시글 수정정보가 주어지면 게시글을 수정한다.")
		@Test
		void givenArticleEditInfo_whenEditsArticle_thenDoNothing() {
			// given
			articleRepository.save(new Article(null, "bruni", "제목", "내용", LocalDateTime.now()));
			ArticleEditRequest request = new ArticleEditRequest("수정된 제목", "수정된 내용");

			// when
			articleService.editArticle(1L, request);

			// then
			ArticleRequest articleRequest = articleService.findById(1L);
			SoftAssertions.assertSoftly(softAssertions -> {
				softAssertions.assertThat(articleRequest.getTitle()).isEqualTo("수정된 제목");
				softAssertions.assertThat(articleRequest.getContent()).isEqualTo("수정된 내용");
			});
		}

		@DisplayName("존재하지 않는 게시글 아이디가 주어지면 예외를 던진다.")
		@Test
		void givenNotExistsArticleId_whenEditsArticle_thenThrowsException() {
			// given
			ArticleEditRequest request = new ArticleEditRequest("수정된 제목", "수정된 내용");

			// when & then
			assertThatThrownBy(() -> articleService.editArticle(1L, request))
				.isInstanceOf(NotFoundException.class);
		}
	}

	@DisplayName("게시글을 삭제할 때")
	@Nested
	class DeleteArticleTest {

		@DisplayName("삭제에 성공한다.")
		@Test
		void givenNothing_whenDeletesArticle_thenDoNothing() {
			// given
			articleRepository.save(new Article(null, "bruni", "제목", "내용", LocalDateTime.now()));

			// when & then
			assertThatCode(() -> articleService.deleteArticle(1L))
				.doesNotThrowAnyException();
		}

		@DisplayName("존재하지 않는 게시글 아이디가 주어지면 예외를 던진다.")
		@Test
		void givenNotExistsArticleId_whenDeletesArticle_thenThrowsException() {
			// given

			// when & then
			assertThatThrownBy(() -> articleService.deleteArticle(1L))
				.isInstanceOf(NotFoundException.class);
		}
	}
}
