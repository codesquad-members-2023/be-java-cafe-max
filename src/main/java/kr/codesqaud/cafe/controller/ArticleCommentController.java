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

import kr.codesqaud.cafe.controller.dto.req.ReplyRequest;
import kr.codesqaud.cafe.service.ArticleCommentService;

@RestController
public class ArticleCommentController {

	private final ArticleCommentService articleCommentService;

	public ArticleCommentController(ArticleCommentService articleCommentService) {
		this.articleCommentService = articleCommentService;
	}

	@PostMapping("/comments")
	public ResponseEntity<Long> reply(@RequestBody final ReplyRequest request,
									  @SessionAttribute(SESSION_USER) final String userId) {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(articleCommentService.reply(request, userId));
	}

	@DeleteMapping("/comments/{articleCommentId}")
	public ResponseEntity<Long> deleteComment(@PathVariable final Long articleCommentId,
											  @SessionAttribute(SESSION_USER) final String userId) {
		articleCommentService.validateHasAuthorization(articleCommentId, userId);
		articleCommentService.deleteById(articleCommentId);

		return ResponseEntity.status(HttpStatus.OK)
			.body(articleCommentId);
	}
}
