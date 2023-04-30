package kr.codesqaud.cafe.controller;

import static kr.codesqaud.cafe.common.consts.SessionConst.SESSION_USER;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import kr.codesqaud.cafe.controller.dto.CommentSaveResponse;
import kr.codesqaud.cafe.controller.dto.req.CommentRequest;
import kr.codesqaud.cafe.service.CommentService;

@RestController
public class CommentController {

	private final CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	@PostMapping("/comments")
	public ResponseEntity<CommentSaveResponse> writeComment(@RequestBody final CommentRequest request,
	                                                        @SessionAttribute(SESSION_USER) final String userId) {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(commentService.reply(request, userId));
	}

	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<Long> deleteComment(@PathVariable final Long commentId,
	                                          @SessionAttribute(SESSION_USER) final String userId) {
		commentService.checkDeleteCommentPermission(commentId, userId);
		commentService.deleteById(commentId);

		return ResponseEntity.status(HttpStatus.OK)
			.body(commentId);
	}
}
