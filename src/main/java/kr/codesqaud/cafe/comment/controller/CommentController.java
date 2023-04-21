package kr.codesqaud.cafe.comment.controller;

import kr.codesqaud.cafe.comment.dto.RequestForm;
import kr.codesqaud.cafe.comment.service.CommentService;
import kr.codesqaud.cafe.utils.Session;
import org.springframework.http.ResponseEntity;
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
    public Comment createComment(RequestForm requestForm, HttpSession session) {
        return commentService.save(requestForm, Session.getUserId(session));
    }

    }
}
