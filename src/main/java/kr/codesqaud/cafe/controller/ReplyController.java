package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.ReplyDto;
import kr.codesqaud.cafe.controller.dto.request.replyRequest.ReplyEditRequest;
import kr.codesqaud.cafe.controller.dto.request.replyRequest.ReplyRequest;
import kr.codesqaud.cafe.controller.modelBuilder.ModelBuilder;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.service.ReplyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ReplyController {
    private final ReplyService replyService;
    private final ModelBuilder modelBuilder;

    public ReplyController(ReplyService replyService, ModelBuilder modelBuilder) {
        this.replyService = replyService;
        this.modelBuilder = modelBuilder;
    }

    @PostMapping("/{articleId}/reply")
    public String reply(@PathVariable Long articleId, @ModelAttribute final ReplyRequest request, HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession();
        if (session != null) {
            User loginUser = (User) session.getAttribute("loginUser");
            if (loginUser != null){
                replyService.reply(articleId, request, httpRequest);
            }
        }
        return "redirect:/articles/" + articleId;
    }

    @GetMapping("/replies/{replyId}")
    public String showReplyEditForm(@PathVariable final Long replyId, HttpServletRequest httpRequest, Model model) {
        HttpSession session = httpRequest.getSession(false);
        ReplyDto reply = replyService.findByReplyId(replyId);
        Long articleId = reply.getArticleId();
        model.addAttribute("reply", reply);
        model.addAttribute("comment", reply.getComment());

        if (session != null) {
            User loginUser = (User) session.getAttribute("loginUser");
            if (loginUser != null && loginUser.getUserId().equals(reply.getUserId())) {
                return "qna/reply_edit_form";
            }
        }
        modelBuilder.showFailed(articleId, model);
        return "qna/failed";
    }

    @PutMapping("/replies/{replyId}")
    public String editReply(@PathVariable final Long replyId, @ModelAttribute final ReplyEditRequest request, HttpServletRequest httpRequest, Model model) {
        HttpSession session = httpRequest.getSession();
        ReplyDto reply = replyService.findByReplyId(replyId);
        Long articleId = reply.getArticleId();

        if (session != null) {
            User loginUser = (User) session.getAttribute("loginUser");
            if (loginUser != null && loginUser.getUserName().equals(reply.getUserName())) {
                replyService.editReply(replyId, request);
                return "redirect:/articles/" + articleId;
            }
        }
        modelBuilder.showFailed(articleId, model);
        return "qna/failed";
    }

    @DeleteMapping("/replies/{replyId}")
    public String deleteReply(@PathVariable final Long replyId, HttpServletRequest httpRequest, Model model) {
        HttpSession session = httpRequest.getSession();
        ReplyDto reply = replyService.findByReplyId(replyId);
        Long articleId = reply.getArticleId();
        model.addAttribute("reply", reply);

        if (session != null) {
            User loginUser = (User) session.getAttribute("loginUser");
            if (loginUser != null && loginUser.getUserName().equals(reply.getUserName())) {
                replyService.deleteReply(replyId);
                return "redirect:/articles/" + articleId;
            }
        }
        modelBuilder.showFailed(articleId, model);
        return "qna/failed";
    }
}
