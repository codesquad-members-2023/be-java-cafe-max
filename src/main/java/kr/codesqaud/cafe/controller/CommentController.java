package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.comment.CommentDTO;
import kr.codesqaud.cafe.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static kr.codesqaud.cafe.util.LoginSessionManager.LOGIN_USER;

@Controller
public class CommentController {

    private final CommentService commentService;


    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{articleId}/new")
    public String write(@PathVariable long articleId, CommentDTO commentDTO, HttpSession session) {
        if(isAnonymous(session)) return "redirect:/login";
        commentService.write(articleId, commentDTO);
        return "redirect:/posts/{articleId}";
    }

    private boolean isAnonymous(HttpSession session) {
        return session.getAttribute(LOGIN_USER) == null;
    }
}
