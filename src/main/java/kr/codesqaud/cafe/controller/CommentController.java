package kr.codesqaud.cafe.controller;

import static kr.codesqaud.cafe.util.SignInSessionUtil.SIGN_IN_SESSION_NAME;

import javax.validation.Valid;
import kr.codesqaud.cafe.config.session.AccountSession;
import kr.codesqaud.cafe.dto.comment.CommentDeleteResponse;
import kr.codesqaud.cafe.dto.comment.CommentResponse;
import kr.codesqaud.cafe.dto.comment.CommentWriteRequest;
import kr.codesqaud.cafe.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RequestMapping("/api/posts/{postId}/comments")
@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentResponse> write(@PathVariable Long postId,
        @RequestBody @Valid CommentWriteRequest commentWriteRequest,
        @SessionAttribute(SIGN_IN_SESSION_NAME) AccountSession accountSession) {
        commentWriteRequest.initializeWriterAndPostId(accountSession, postId);
        return new ResponseEntity<>(commentService.write(commentWriteRequest), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommentDeleteResponse> delete(@PathVariable Long id,
        @SessionAttribute(SIGN_IN_SESSION_NAME) AccountSession accountSession) {
        return new ResponseEntity<>(commentService.delete(id, accountSession.getId()), HttpStatus.OK);
    }
}
