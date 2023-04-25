package kr.codesqaud.cafe.question_comment.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.codesqaud.cafe.common.auth.exception.NoAuthSessionException;
import kr.codesqaud.cafe.common.auth.utill.AuthSessionValidator;
import kr.codesqaud.cafe.question_comment.controller.request.CommentWriteRequestDTO;
import kr.codesqaud.cafe.question_comment.service.CommentService;
import kr.codesqaud.cafe.user.controller.response.AuthSession;

@Controller
@RequestMapping("/questions")
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
	@PostMapping("/{post_id}/comments")
	public String replyAdd(@PathVariable long post_id, CommentWriteRequestDTO dto, HttpSession session) throws
		NoAuthSessionException {
		AuthSession authSession = AuthSessionValidator.validateUserIsSignedIn(session);
		service.save(dto.toEntity(post_id, authSession.getId(), authSession.getUserId()));

		return "redirect:/questions/" + post_id;
	}

}
