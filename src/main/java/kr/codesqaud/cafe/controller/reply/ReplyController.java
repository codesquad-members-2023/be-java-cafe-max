package kr.codesqaud.cafe.controller.reply;

import kr.codesqaud.cafe.domain.dto.reply.ReplyForm;
import kr.codesqaud.cafe.service.reply.ReplyService;
import kr.codesqaud.cafe.session.SessionConst;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @DeleteMapping("/replies/{id}")
    public String deleteArticle(@PathVariable Long id, Long articleId) {
        // TODO: validate 추가하기

        replyService.delete(id);
        // TODO: 아래와 같이 쓰면 왜 String으로 들어오는지 알아내기
        return "redirect:/questions/" + articleId;
    }
}
