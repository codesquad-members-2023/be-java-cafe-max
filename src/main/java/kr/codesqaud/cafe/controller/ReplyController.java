package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.constant.SessionConst;
import kr.codesqaud.cafe.dto.ReplyForm;
import kr.codesqaud.cafe.dto.SessionDto;
import kr.codesqaud.cafe.service.ReplyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ReplyController {

    private final ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping("articles/{id}/reply")
    public String write(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                            SessionDto loginUser, ReplyForm form, @PathVariable Long id, RedirectAttributes redirectAttributes) {

        boolean isWriteReplySuccess = replyService.write(loginUser.getUserId(), loginUser.getName(), id, form);
        if (!isWriteReplySuccess) {
            redirectAttributes.addFlashAttribute("emptyComment", true);
        }

        return "redirect:/articles/{id}";
    }
}
