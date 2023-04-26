package kr.codesqaud.cafe.app.comment.controller;

import javax.validation.Valid;
import kr.codesqaud.cafe.app.comment.controller.dto.CommentSavedRequest;
import kr.codesqaud.cafe.app.comment.controller.dto.CommentResponse;
import kr.codesqaud.cafe.app.comment.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/qna/{id}")
public class CommentController {

    private static final Logger log = LoggerFactory.getLogger(CommentController.class);

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/comments")
    public ModelAndView listComment(@PathVariable(value = "id") Long questionId) {
        return null;
    }

    @PostMapping("/comments")
    public CommentResponse createComment(
        @PathVariable(value = "id") Long questionId,
        @Valid @RequestBody CommentSavedRequest commentRequest) {
        log.info("questionId : {}", questionId);
        log.info("commentRequest : {}", commentRequest);
        return commentService.answerComment(commentRequest);
    }

    @PutMapping("/comments/{commentId}")
    public CommentResponse modifyComment(@PathVariable(value = "id") Long questionId,
        @PathVariable(value = "commentId") Long commentId,
        @Valid @RequestBody CommentSavedRequest commentSavedRequest) {
        return null;
    }

    @DeleteMapping("/comments/{commentId}")
    public CommentResponse deleteComment(@PathVariable(value = "id") Long questionId,
        @PathVariable(value = "commentId") Long commentId) {
        return null;
    }
}
