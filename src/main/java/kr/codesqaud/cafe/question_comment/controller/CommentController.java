package kr.codesqaud.cafe.question_comment.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.codesqaud.cafe.common.auth.exception.NoAccessPermissionException;
import kr.codesqaud.cafe.common.auth.exception.NoAuthSessionException;
import kr.codesqaud.cafe.common.auth.utill.AuthSessionValidator;
import kr.codesqaud.cafe.common.web.CursorPageHandler;
import kr.codesqaud.cafe.question_comment.controller.request.CommentWriteRequestDTO;
import kr.codesqaud.cafe.question_comment.controller.response.CommentResponseDTO;
import kr.codesqaud.cafe.question_comment.domain.CommentEntity;
import kr.codesqaud.cafe.question_comment.exception.CommentNotExistException;
import kr.codesqaud.cafe.question_comment.service.CommentService;
import kr.codesqaud.cafe.user.controller.response.AuthSession;

@RestController
@RequestMapping("/questions/{post_id}/comments")
public class CommentController {
	private final CommentService service;

	public CommentController(CommentService service) {
		this.service = service;
	}

	/**
	 * 댓글 추가
	 * @param post_id 댓글을 추가할 게시글의 id
	 * @param dto 추가할 댓글의 정보
	 * @param session 세션
	 * @return 댓글 추가에 성공한 경우 댓글이 달린 게시글 보기 페이지로 이동
	 * @throws NoAuthSessionException 로그인 하지 않은 상태인 경우 예외 발생
	 */
	@PostMapping
	public CommentResponseDTO commentAdd(@PathVariable long post_id, CommentWriteRequestDTO dto,
		HttpSession session) throws
		NoAuthSessionException, CommentNotExistException {

		AuthSession authSession = AuthSessionValidator.validateUserIsSignedIn(session);

		long id = service.save(dto.toEntity(post_id, authSession.getId(), authSession.getUserId()));

		return CommentResponseDTO.from(service.findById(id));
	}

	/**
	 * 커서 기반 페이징 댓글 리스트 불러오기
	 * @param post_id 불러올 댓글이 달린 게시글 id
	 * @param cursor 몇 번 째 댓글부터 불러올 것인지
	 * @param session 세션
	 * @return 댓글 List 반환
	 * @throws NoAuthSessionException 로그인 하지 않은 상태이면 예외 발생
	 */
	@GetMapping
	public List<CommentResponseDTO> commentList(@PathVariable long post_id,
		@RequestParam(value = "cursor", defaultValue = "-1") long cursor,
		HttpSession session) throws NoAuthSessionException {

		AuthSession authSession = AuthSessionValidator.validateUserIsSignedIn(session);

		CursorPageHandler cursorPageHandler = new CursorPageHandler(cursor);

		return service.findBy(post_id, cursorPageHandler.getCursor(), cursorPageHandler.getSize())
			.stream()
			.map(CommentResponseDTO::from)
			.collect(
				Collectors.toUnmodifiableList());
	}

	/**
	 * 댓글 삭제
	 * @param post_id 댓글이 달린 게시글 id
	 * @param id 삭제할 댓글 id
	 * @param session 세션
	 * @return 댓글이 달린 게시글 상세 보기 페이지로 이동
	 * @throws CommentNotExistException 댓글이 존재하지 않을 때 예외 발생
	 * @throws NoAccessPermissionException 댓글 작성자가 아닌 경우 예외 발생
	 */
	@DeleteMapping("/{id}")
	public long commentDelete(@PathVariable long post_id, @PathVariable long id,
		HttpSession session) throws
		CommentNotExistException,
		NoAccessPermissionException {

		CommentEntity comment = service.findById(id);
		AuthSessionValidator.validatePageOnlyWriterCanAccess(session, comment.getWriter_id());

		service.deleteById(id);

		return 1;
	}

}
