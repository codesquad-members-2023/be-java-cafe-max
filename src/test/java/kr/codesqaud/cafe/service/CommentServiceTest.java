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

import kr.codesqaud.cafe.controller.dto.req.CommentRequest;
import kr.codesqaud.cafe.domain.comment.Comment;
import kr.codesqaud.cafe.exception.NoAuthorizationException;
import kr.codesqaud.cafe.repository.CommentRepository;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

	@Mock
	private CommentRepository commentRepository;

	@InjectMocks
	private CommentService commentService;

	@DisplayName("댓글 등록할 때")
	@Nested
	class ReplyTest {

		@DisplayName("댓글 정보가 주어지면 댓글 등록할 때 성공한다.")
		@Test
		void givenReplyRequest_whenReply_thenReturnsNothing() {
			// given
			CommentRequest commentRequest = new CommentRequest(1L, "댓글 등록 테스트~");
			given(commentRepository.save(any(Comment.class))).willReturn(Optional.of(createArticleComment()));

			// when
			commentService.reply(commentRequest, "bruni");

			// then
			then(commentRepository).should().save(any(Comment.class));
		}
	}

	@DisplayName("댓글 권한을 검증할 때")
	@Nested
	class ValidateHasAuthorizationTest {

		@DisplayName("댓글의 작성자와 현재 로그인한 사용자가 일치하면 검증에 성공한다.")
		@Test
		void givenArticleCommentIdAndUserId_whenValidateHasAuthorization_thenDoNothing() {
			// given
			given(commentRepository.findById(anyLong())).willReturn(Optional.of(createArticleComment()));

			// when
			commentService.checkDeleteCommentPermission(1L, "bruni");

			// then
			then(commentRepository).should().findById(1L);
		}

		@DisplayName("댓글의 작성자와 현재 로그인한 사용자가 일치하지 않으면 예외를 던진다.")
		@Test
		void givenNotEqualsCommentWriterAndUserId_whenValidateHasAuthorization_thenThrowsException() {
			// given
			given(commentRepository.findById(anyLong())).willReturn(Optional.of(createArticleComment()));

			// when & then
			assertAll(
				() -> assertThatThrownBy(() -> commentService.checkDeleteCommentPermission(1L, "unknown"))
					.isInstanceOf(NoAuthorizationException.class),
				() -> then(commentRepository).should().findById(1L)
			);
		}

		@DisplayName("댓글을 삭제할 때")
		@Nested
		class DeleteTest {

			@DisplayName("댓글 아이디가 주어지면 댓글 삭제에 성공한다.")
			@Test
			void givenArticleCommentId_whenDeletesArticleComment_thenDoNothing() {
				// given
				willDoNothing().given(commentRepository).deleteById(anyLong());

				// when
				assertAll(
					() -> assertThatCode(() -> commentService.deleteById(1L))
						.doesNotThrowAnyException(),
					() -> then(commentRepository).should().deleteById(1L)
				);
			}
		}
	}
}
