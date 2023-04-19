package kr.codesqaud.cafe.controller.reply;

import kr.codesqaud.cafe.domain.dto.reply.ReplyForm;
import kr.codesqaud.cafe.service.reply.ReplyService;
import kr.codesqaud.cafe.session.SessionConst;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class ReplyController {
    private final ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping("/questions/{articleId}/replies")
    public String postReply(@PathVariable Long articleId, ReplyForm form, HttpSession session) {
        replyService.add(articleId, form, (String) session.getAttribute(SessionConst.LOGIN_USER_ID));
        return "redirect:/questions/{articleId}";
    }
}
