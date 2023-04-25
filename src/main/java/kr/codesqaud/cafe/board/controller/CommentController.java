package kr.codesqaud.cafe.board.controller;

import kr.codesqaud.cafe.board.dto.CommentResponse;
import kr.codesqaud.cafe.board.dto.CommentWriteForm;
import kr.codesqaud.cafe.board.service.CommentService;
import kr.codesqaud.cafe.user.dto.SessionUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/board/{postId}/comment")
    public CommentResponse writeComment(@PathVariable Long postId, @ModelAttribute CommentWriteForm commentWriteForm, @SessionAttribute("sessionUser") SessionUser sessionUser) {
        Long commentId = commentService.write(commentWriteForm, sessionUser.getUserName());
        return commentService.getComment(commentId);
    }

    @DeleteMapping("/board/{postId}/comment/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long postId, @PathVariable Long commentId, @SessionAttribute("sessionUser") SessionUser sessionUser) {
        if (!commentService.isSameWriter(commentId, sessionUser.getUserName())) {
            return new ResponseEntity<>("작성자가 아니어서 삭제할 수 없습니다.", HttpStatus.BAD_REQUEST);
        }
        commentService.delete(commentId);
        return new ResponseEntity<>("성공", HttpStatus.OK);
    }

}
