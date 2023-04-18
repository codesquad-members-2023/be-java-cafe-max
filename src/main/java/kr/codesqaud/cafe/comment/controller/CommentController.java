package kr.codesqaud.cafe.comment.controller;

import kr.codesqaud.cafe.comment.dto.RequestForm;
import kr.codesqaud.cafe.comment.service.CommentService;
import kr.codesqaud.cafe.utils.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public String createComment(RequestForm requestForm, HttpSession session){
        if(Session.isLoggedIn(session)){
            commentService.save(requestForm, Session.getUserId(session));
            return "redirect:/articles/" + requestForm.getArticleId();
        }
        return "redirect:/user/login";
    }
}
