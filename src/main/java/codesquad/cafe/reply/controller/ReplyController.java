package codesquad.cafe.reply.controller;

import codesquad.cafe.reply.dto.ReplyRequestDto;
import codesquad.cafe.reply.service.ReplyService;
import codesquad.cafe.user.domain.User;
import codesquad.cafe.global.constant.SessionAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @DeleteMapping("/reply/{postId}/{replyId}")
    public String deleteReply(final @PathVariable Long postId,
                              final @PathVariable Long replyId,
                              HttpSession session) {
        User user = (User) session.getAttribute(SessionAttributes.LOGIN_USER.getValue());
        replyService.deleteReply(user, replyId);
        return "redirect:/articles/" + postId;
    }
}
