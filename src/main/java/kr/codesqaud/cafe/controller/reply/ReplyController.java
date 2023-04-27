package kr.codesqaud.cafe.controller.reply;

import kr.codesqaud.cafe.controller.article.ArticleForm;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.service.ReplyService;
import kr.codesqaud.cafe.util.SessionConst;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/answers/{replyId}/edit")
    public String editForm(@PathVariable Long replyId, Model model, HttpSession session){
        if (!replyService.validateUserIdDuplicate(replyId, session)){
            model.addAttribute("id", articleId);
            return "qna/edit_failed";
        }

        model.addAttribute("reply", replyService.findOne(replyId));
        return "qna/edit_reply";
    }

    @PutMapping("/answers/{replyId}/edit")
    public String edit(@PathVariable Long replyId, String contents) {
        Long articleId = replyService.findOne(replyId).getArticleId();
        replyService.update(replyId, contents);

        return "redirect:/questions/" + articleId;
    }

    @DeleteMapping("/answers/{replyId}/delete")
    public String delete(@PathVariable Long replyId, HttpSession session) {
        if (!replyService.validateUserIdDuplicate(replyId, session)) {
            model.addAttribute("id", articleId);
            return "qna/edit_failed";
        }

        Long articleId = replyService.findOne(replyId).getArticleId();
        replyService.delete(replyId);

        return "redirect:/questions/" + articleId;
    }
}
