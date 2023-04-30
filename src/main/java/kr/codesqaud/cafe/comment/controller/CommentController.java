package kr.codesqaud.cafe.comment.controller;

import kr.codesqaud.cafe.comment.domain.Comment;
import kr.codesqaud.cafe.comment.dto.RequestCommentForm;
import kr.codesqaud.cafe.comment.service.CommentService;
import kr.codesqaud.cafe.utils.Session;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public Comment createComment(RequestCommentForm requestCommentForm, HttpSession session) {
        return commentService.save(requestCommentForm, Session.getUserId(session));
    }

    @DeleteMapping
    public HttpStatus deleteComment(long commentId, String userId, HttpSession session) {
        return commentService.delete(commentId, userId, Session.getUserId(session));
    }
}
