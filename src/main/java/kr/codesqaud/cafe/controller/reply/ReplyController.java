package kr.codesqaud.cafe.controller.reply;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.service.ReplyService;
import kr.codesqaud.cafe.util.SessionConst;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class ReplyController {

    private final ReplyService replyService;

    public ReplyController(ReplyService replyService){
        this.replyService = replyService;
    }

    @PostMapping("/answers/{articleId}")
    @PostMapping("questions/{id}/answers")
    public String create(@PathVariable Long articleId, String contents, HttpSession session) {
        User user = (User) session.getAttribute(SessionConst.LOGIN_USER);
        replyService.post(user.getUserId(), contents, articleId);
        return "redirect:/questions/{articleId}";
    @PutMapping("/answers/{replyId}/edit")
    @DeleteMapping("/answers/{replyId}/delete")
    }
}
