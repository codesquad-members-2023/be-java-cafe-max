package codesquad.cafe.domain.reply.controller;

import codesquad.cafe.domain.reply.dto.ReplyRequestDto;
import codesquad.cafe.domain.reply.service.ReplyService;
import codesquad.cafe.domain.user.domain.User;
import codesquad.cafe.global.constant.SessionAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class ReplyController {

    private final ReplyService replyService;

    public ReplyController(final ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping("/reply/{postId}")
    public String createReply(final @PathVariable Long postId,
                              final @ModelAttribute ReplyRequestDto replyRequestDto,
                              HttpSession session) {
        User user = (User) session.getAttribute(SessionAttributes.LOGIN_USER.getValue());
        replyService.createReply(postId, user, replyRequestDto);
        return "redirect:/articles/" + postId;
    }
}
