package kr.codesqaud.cafe.service;

import static kr.codesqaud.cafe.fixture.FixtureFactory.createArticleComment;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kr.codesqaud.cafe.controller.dto.req.ReplyRequest;
import kr.codesqaud.cafe.domain.articlecomment.ArticleComment;
import kr.codesqaud.cafe.exception.NoAuthorizationException;
import kr.codesqaud.cafe.repository.ArticleCommentRepository;

@ExtendWith(MockitoExtension.class)
class ArticleCommentServiceTest {

	@Mock
	private ArticleCommentRepository articleCommentRepository;

	@InjectMocks
	private ArticleCommentService articleCommentService;

	@DisplayName("댓글 등록할 때")
	@Nested
	class ReplyTest {

		@DisplayName("댓글 정보가 주어지면 댓글 등록할 때 성공한다.")
		@Test
		void givenReplyRequest_whenReply_thenReturnsNothing() {
			// given
			ReplyRequest replyRequest = new ReplyRequest(1L, "댓글 등록 테스트~");
			given(articleCommentRepository.save(any(ArticleComment.class))).willReturn(
				Optional.of(createArticleComment()));

			// when
			articleCommentService.reply(replyRequest, "bruni");

			// then
			then(articleCommentRepository).should().save(any(ArticleComment.class));
		}
	}

	@DisplayName("댓글 권한을 검증할 때")
	@Nested
	class ValidateHasAuthorizationTest {

		@DisplayName("댓글의 작성자와 현재 로그인한 사용자가 일치하면 검증에 성공한다.")
		@Test
		void givenArticleCommentIdAndUserId_whenValidateHasAuthorization_thenDoNothing() {
			// given
			given(articleCommentRepository.findById(anyLong())).willReturn(Optional.of(createArticleComment()));

			// when & then
			assertAll(
				() -> articleCommentService.validateHasAuthorization(1L, "bruni"),
				() -> then(articleCommentRepository).should().findById(1L)
			);
		}

		@DisplayName("댓글의 작성자와 현재 로그인한 사용자가 일치하지 않으면 예외를 던진다.")
		@Test
		void givenNotEqualsCommentWriterAndUserId_whenValidateHasAuthorization_thenThrowsException() {
			// given
			given(articleCommentRepository.findById(anyLong())).willReturn(Optional.of(createArticleComment()));

			// when & then
			assertAll(
				() -> assertThatThrownBy(() -> articleCommentService.validateHasAuthorization(1L, "unknown"))
					.isInstanceOf(NoAuthorizationException.class),
				() -> then(articleCommentRepository).should().findById(1L)
			);
		}

		@DisplayName("댓글을 삭제할 때")
		@Nested
		class DeleteTest {

			@DisplayName("댓글 아이디가 주어지면 댓글 삭제에 성공한다.")
			@Test
			void givenArticleCommentId_whenDeletesArticleComment_thenDoNothing() {
				// given
				willDoNothing().given(articleCommentRepository).deleteById(anyLong());

				// when
				assertAll(
					() -> assertThatCode(() -> articleCommentService.deleteById(1L))
						.doesNotThrowAnyException(),
					() -> then(articleCommentRepository).should().deleteById(1L)
				);
			}
		}
	}
}
