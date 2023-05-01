package kr.codesqaud.cafe.board.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.codesqaud.cafe.board.dto.CommentResponse;
import kr.codesqaud.cafe.board.dto.CommentWriteForm;
import kr.codesqaud.cafe.board.service.CommentService;
import kr.codesqaud.cafe.user.dto.SessionUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"댓글 API"})
@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @ApiOperation(value = "게시글에 댓글 작성")
    @PostMapping("/board/{postId}")
    public CommentResponse writeComment(@PathVariable Long postId, @ModelAttribute CommentWriteForm commentWriteForm, @SessionAttribute SessionUser sessionUser) {
        Long commentId = commentService.write(commentWriteForm, sessionUser.getUserName());
        return commentService.getComment(commentId);
    }

    @ApiOperation(value = "게시글에 해당 댓글 삭제")
    @DeleteMapping("/board/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long postId, @PathVariable Long commentId, @SessionAttribute SessionUser sessionUser) {
        if (!commentService.isSameWriter(commentId, sessionUser.getUserName())) {
            return new ResponseEntity<>("작성자가 아니어서 삭제할 수 없습니다.", HttpStatus.BAD_REQUEST);
        }
        commentService.delete(commentId);
        return new ResponseEntity<>("성공", HttpStatus.OK);
    }

}
