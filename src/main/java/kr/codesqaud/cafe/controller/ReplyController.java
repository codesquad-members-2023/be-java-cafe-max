package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.constant.SessionConst;
import kr.codesqaud.cafe.dto.Answer;
import kr.codesqaud.cafe.dto.Result;
import kr.codesqaud.cafe.dto.SessionDto;
import kr.codesqaud.cafe.exception.UnauthorizedException;
import kr.codesqaud.cafe.service.ReplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


@RestController
public class ReplyController {

    private final ReplyService replyService;
    private static final Logger logger = LoggerFactory.getLogger(ReplyController.class);

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping("articles/{id}/reply")
    public Answer write(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                            SessionDto loginUser, @RequestParam String contents, @PathVariable Long id) {

        Long replyId = replyService.write(loginUser.getUserId(), loginUser.getName(), id, contents);
        logger.info("userId={}", loginUser.getUserId());
        return new Answer(replyId,loginUser.getUserId(), id, contents);

    }

    @DeleteMapping("articles/{articleId}/reply/{id}")
    public Result delete(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                             SessionDto loginUser, @PathVariable Long articleId, @PathVariable Long id) {

        logger.info("DeleteMapping");
        if (!replyService.isCreateBy(loginUser.getUserId(), id)) {
            logger.info("권한 없어 댓글 삭제 불가");
            throw new UnauthorizedException();
        }
        replyService.delete(id);
        return new Result("success");
    }
}
