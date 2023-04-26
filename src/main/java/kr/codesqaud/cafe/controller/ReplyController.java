package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.ReplyDto;
import kr.codesqaud.cafe.controller.dto.request.ReplyEditRequest;
import kr.codesqaud.cafe.controller.dto.request.ReplyRequest;
import kr.codesqaud.cafe.domain.Reply;
import kr.codesqaud.cafe.service.ArticleService;
import kr.codesqaud.cafe.service.ReplyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ReplyController {
    private final ReplyService replyService;
    private final ArticleService articleService;

    public ReplyController(ReplyService replyService, ArticleService articleService) {
        this.replyService = replyService;
        this.articleService = articleService;
    }

    @PostMapping("/{articleId}/reply")
    public String reply(@PathVariable Long articleId, @ModelAttribute final ReplyRequest request, HttpServletRequest httpRequest, Model model) {
        replyService.reply(articleId, request, httpRequest);
        model.addAttribute("article", articleService.findById(articleId));
        return "qna/show";
    }

    @GetMapping("/replies/{replyId}")
    public String showReplyEditForm(@PathVariable final Long replyId, HttpServletRequest httpRequest, Model model) {
        HttpSession session = httpRequest.getSession(false);
        ReplyDto reply = replyService.findByReplyId(replyId);

        model.addAttribute("reply", reply);
        model.addAttribute("comment", reply.getComment());

        if (session != null && session.getAttribute("userId").equals(reply.getUserId())) {
            return "qna/reply_edit_form";
        }

        return "qna/failed";
    }

    @PutMapping("/replies/{replyId}")
    public String editReply(@PathVariable final Long replyId, @ModelAttribute final ReplyEditRequest request) {
        Reply reply = replyService.editReply(replyId, request);
        Long articleId = reply.getArticleId();
        return "redirect:/articles/" + articleId;
    }

    @DeleteMapping("/replies/{replyId}")
    public String deleteReply(@PathVariable final Long replyId, HttpServletRequest httpRequest, Model model) {
        HttpSession session = httpRequest.getSession();
        ReplyDto reply = replyService.findByReplyId(replyId);
        model.addAttribute("reply", reply);
        if (session != null && session.getAttribute("userName").equals(reply.getUserName())) {
            replyService.deleteReply(replyId);
            return "qna/show";
        }
        return "qna/failed";
    }
}
