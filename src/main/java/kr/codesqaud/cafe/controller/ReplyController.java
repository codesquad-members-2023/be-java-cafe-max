package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.constant.SessionConst;
import kr.codesqaud.cafe.dto.Answer;
import kr.codesqaud.cafe.dto.Result;
import kr.codesqaud.cafe.dto.SessionDto;
import kr.codesqaud.cafe.exception.UnauthorizedException;
import kr.codesqaud.cafe.service.ArticleService;
import kr.codesqaud.cafe.service.ReplyService;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReplyController {

    private final ReplyService replyService;
    private final UserService userService;
    private final ArticleService articleService;

    public ReplyController(ReplyService replyService, UserService userService, ArticleService articleService) {
        this.replyService = replyService;
        this.userService = userService;
        this.articleService = articleService;
    }

    @PostMapping("articles/{id}/reply")
    public Answer write(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                            SessionDto loginUser, @RequestParam String contents, @PathVariable Long id) {

        Long replyId = replyService.write(loginUser.getUserId(), loginUser.getName(), id, contents);

        return new Answer(replyId,loginUser.getUserId(), id, contents);

    }

    @DeleteMapping("articles/{articleId}/reply/{id}")
    public Result delete(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                             SessionDto loginUser, @PathVariable Long articleId, @PathVariable Long id) {

        if (replyService.isCreateBy(loginUser.getUserId(), id)) {
            replyService.delete(id);
            return new Result("success");
        }
        throw new UnauthorizedException();
    }
}
