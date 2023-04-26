package kr.codesqaud.cafe.app.comment.controller;

import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import kr.codesqaud.cafe.app.comment.controller.dto.CommentResponse;
import kr.codesqaud.cafe.app.comment.controller.dto.CommentSavedRequest;
import kr.codesqaud.cafe.app.comment.service.CommentService;
import kr.codesqaud.cafe.app.user.controller.dto.UserResponse;
import kr.codesqaud.cafe.errors.errorcode.UserErrorCode;
import kr.codesqaud.cafe.errors.exception.RestApiException;
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

    // 댓글 전체 조회
    @GetMapping("/comments")
    public List<CommentResponse> listComment(@PathVariable(value = "id") Long questionId) {
        return commentService.getComments(questionId);
    }

    // 댓글 추가
    @PostMapping("/comments")
    public CommentResponse createComment(
        @PathVariable(value = "id") Long questionId,
        @Valid @RequestBody CommentSavedRequest commentRequest) {
        log.info("questionId : {}", questionId);
        log.info("commentRequest : {}", commentRequest);
        return commentService.answerComment(commentRequest);
    }

    // TOOD: 권한 인터셉터 필요
    // 댓글 수정
    @PutMapping("/comments/{commentId}")
    public CommentResponse modifyComment(@PathVariable(value = "id") Long questionId,
        @PathVariable(value = "commentId") Long commentId,
        @Valid @RequestBody CommentSavedRequest commentRequest,
        HttpSession session) {
        log.info("questionId : {}, commentRequest : {}", questionId, commentRequest);

        UserResponse user = (UserResponse) session.getAttribute("user");
        CommentResponse comment = commentService.getComment(commentId);
        if (!comment.getUserId().equals(user.getId())) {
            throw new RestApiException(UserErrorCode.PERMISSION_DENIED);
        }

        return commentService.modifyComment(commentRequest);
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public CommentResponse deleteComment(@PathVariable(value = "id") Long questionId,
        @PathVariable(value = "commentId") Long commentId,
        HttpSession session) {
        UserResponse user = (UserResponse) session.getAttribute("user");
        CommentResponse comment = commentService.getComment(commentId);
        if (!comment.getUserId().equals(user.getId())) {
            throw new RestApiException(UserErrorCode.PERMISSION_DENIED);
        }

        return commentService.deleteComment(commentId);
    }

    // 댓글 수정 페이지
    @GetMapping("/comments/{commentId}/edit")
    public ModelAndView editCommentForm(@PathVariable(value = "id") Long questionId,
        @PathVariable(value = "commentId") Long commentId) {
        ModelAndView mav = new ModelAndView("comment/edit");
        mav.addObject("comment", commentService.getComment(commentId));
        return mav;
    }
}
