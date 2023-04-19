package kr.codesquad.cafe.post;

import kr.codesquad.cafe.comment.Comment;
import kr.codesquad.cafe.comment.CommentService;
import kr.codesquad.cafe.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ApiPostController {

    private final PostService postService;

    private final CommentService commentService;

    public ApiPostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comments")
    public String addComment(@RequestParam("commentText") String content, @PathVariable("postId") Post post, @SessionAttribute User user, Model model) {
        Comment comment = commentService.from(content, post, user);
        Post save = postService.save(post, comment);
        model.addAttribute("post", save);
        return "post/detail :: #commentsContent";
    }
}
