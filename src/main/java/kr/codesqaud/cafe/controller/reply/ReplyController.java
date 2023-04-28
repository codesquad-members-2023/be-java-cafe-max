package kr.codesqaud.cafe.controller.reply;

import io.swagger.annotations.Api;
import kr.codesqaud.cafe.domain.dto.Result;
import kr.codesqaud.cafe.domain.dto.reply.ReplyForm;
import kr.codesqaud.cafe.domain.dto.reply.ReplyTimeForm;
import kr.codesqaud.cafe.service.reply.ReplyService;
import kr.codesqaud.cafe.session.SessionConst;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@Api(tags = "댓글 정보")
public class ReplyController {
    private final ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping("/questions/{articleId}/replies")
    public ReplyTimeForm postReply(@PathVariable Long articleId, ReplyForm form, HttpSession session) {
        return replyService.saveReply(articleId, form, (String) session.getAttribute(SessionConst.LOGIN_USER_ID));
    }

    @DeleteMapping("/questions/{articleId}/replies/{id}")
    public Result deleteReply(@PathVariable Long id, HttpSession session) {
        validateReplyId(id, (String) session.getAttribute(SessionConst.LOGIN_USER_ID));

        replyService.delete(id);
        return Result.ok();
    }

    private void validateReplyId(Long id, String loginUserId) {
        ReplyTimeForm replyId = replyService.findReplyId(id);

        if (!loginUserId.equals(replyId.getUserId())) {
            throw new IllegalArgumentException("자신이 작성한 댓글이어야 합니다.");
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public Result illegalApiExHandler(IllegalArgumentException e) {
        return Result.fail(e.getMessage());
    }
}
