package kr.codesqaud.cafe.controller;

import static kr.codesqaud.cafe.util.SignInSessionUtil.SIGN_IN_SESSION_NAME;

import java.util.List;
import javax.validation.Valid;
import kr.codesqaud.cafe.config.session.AccountSession;
import kr.codesqaud.cafe.dto.comment.CommentDeleteResponse;
import kr.codesqaud.cafe.dto.comment.CommentPagination;
import kr.codesqaud.cafe.dto.comment.CommentResponse;
import kr.codesqaud.cafe.dto.comment.CommentWriteRequest;
import kr.codesqaud.cafe.dto.comment.CommentsResponse;
import kr.codesqaud.cafe.service.CommentService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RequestMapping("/api/posts/{postId}/comments")
@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public CommentsResponse showComments(@PathVariable Long postId,
        @RequestParam (defaultValue = "1") Integer page) {
        return commentService.findAllByPostId(postId, page);
    }

    @PostMapping
    public CommentResponse write(@PathVariable Long postId,
        @RequestBody @Valid CommentWriteRequest commentWriteRequest,
        @SessionAttribute(SIGN_IN_SESSION_NAME) AccountSession accountSession) {
        commentWriteRequest.initializeWriterAndPostId(accountSession, postId);
        return commentService.write(commentWriteRequest);
    }

    @DeleteMapping("/{id}")
    public CommentDeleteResponse delete(@PathVariable Long id,
        @SessionAttribute(SIGN_IN_SESSION_NAME) AccountSession accountSession) {
        return commentService.delete(id, accountSession.getId());
    }
}
