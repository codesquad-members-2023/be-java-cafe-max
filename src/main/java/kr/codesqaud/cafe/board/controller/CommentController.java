package kr.codesqaud.cafe.board.controller;

import kr.codesqaud.cafe.board.dto.CommentWriteForm;
import kr.codesqaud.cafe.board.service.CommentService;
import kr.codesqaud.cafe.user.dto.SessionUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/board/{postId}/comment")
    public String writeComment(@PathVariable Long postId, @ModelAttribute CommentWriteForm commentWriteForm, @SessionAttribute("sessionUser") SessionUser sessionUser) {
        commentService.write(commentWriteForm, sessionUser.getUserName());
        return "redirect:/board/" + postId;
    }
}
