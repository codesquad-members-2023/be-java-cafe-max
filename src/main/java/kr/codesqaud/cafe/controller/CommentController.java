package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.comment.CommentDTO;
import kr.codesqaud.cafe.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class CommentController {

    private final CommentService commentService;


    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{articleId}/comments")
    public String write(@PathVariable long articleId, CommentDTO commentDTO) {
        commentService.write(articleId, commentDTO);
        return "redirect:/posts/{articleId}";
    }

    @DeleteMapping("/posts/{articleId}/comments/{id}")
    public String delete(@PathVariable long id) {
        commentService.delete(id);
        return "redirect:/posts/{articleId}";
    }
}
