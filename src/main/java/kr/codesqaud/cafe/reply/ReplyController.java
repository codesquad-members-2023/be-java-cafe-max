package kr.codesqaud.cafe.reply;

import javax.servlet.http.HttpSession;
import kr.codesqaud.cafe.web.SessionConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReplyController {

    private final ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping("/articles/{id}/reply")
    public String reply(@PathVariable final long id, final ReplyRequestDto replyRequestDto, final HttpSession session) {
        String userId = (String) session.getAttribute(SessionConstant.LOGIN_USER_ID);
        replyService.save(id, userId, replyRequestDto);
        return "redirect:/articles/{id}";
    }

    // TODO: 댓글 수정 기능 구현 필요
//    @PutMapping("/articles/{articleId}/{replyId}")
//    public String edit(@PathVariable final long replyId, final HttpSession session) {
//        String requesterId = (String) session.getAttribute(SessionConstant.LOGIN_USER_ID);
//        replyService.edit(replyId, requesterId);
//        return "redirect:/articles/{articleId}";
//    }

    @DeleteMapping("/articles/{articleId}/{replyId}")
    public String delete(@PathVariable final long replyId, final HttpSession session) {
        String requesterId = (String) session.getAttribute(SessionConstant.LOGIN_USER_ID);
        replyService.deleteByReplyId(replyId, requesterId);
        return "redirect:/articles/{articleId}";
    }
}
