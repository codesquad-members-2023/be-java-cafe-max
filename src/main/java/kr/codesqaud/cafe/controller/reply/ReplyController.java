package kr.codesqaud.cafe.controller.reply;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.service.ReplyService;
import kr.codesqaud.cafe.util.SessionConst;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpSession;

@Controller
public class ReplyController {

    private final ReplyService replyService;

    public ReplyController(ReplyService replyService){
        this.replyService = replyService;
    }

    @PostMapping("/answers/{articleId}")
    public String post(@PathVariable Long articleId, String contents, HttpSession session) {
        User user = (User) session.getAttribute(SessionConst.LOGIN_USER);
        replyService.post(user.getUserId(), contents, articleId);
        return "redirect:/article/" + articleId;
    }

    @PutMapping("/answers/{replyId}/edit")
    public String edit(@PathVariable Long replyId, String contents, HttpSession session) {
        replyService.update(replyId, contents);
        Long articleId = replyService.findOne(replyId).getArticleId();

        return "redirect:/questions/" + articleId;
    }

    @DeleteMapping("/answers/{replyId}/delete")
    public String delete(@PathVariable Long replyId, HttpSession session) {
            return "qna/edit_failed";
        }
        replyService.delete(replyId);

        Long articleId = replyService.findOne(replyId).getArticleId();
        return "redirect:/questions/" + articleId;
    }
}
