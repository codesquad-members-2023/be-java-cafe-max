package kr.codesquad.cafe.post.controller;

import kr.codesquad.cafe.comment.Comment;
import kr.codesquad.cafe.comment.CommentService;
import kr.codesquad.cafe.post.Post;
import kr.codesquad.cafe.post.PostService;
import kr.codesquad.cafe.user.domain.User;
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
    public String addComment(@RequestParam("commentText") String content, @PathVariable long postId,
                             @SessionAttribute User user, Model model) {
        Post post = postService.findById(postId);
        Comment comment = commentService.save(content, post, user);
        Post savedPost = postService.addComment(post, comment);
        model.addAttribute("post", savedPost);
        return "post/detail :: #commentsContent";
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public String deleteComment(@PathVariable long commentId, @PathVariable long postId, Model model, @SessionAttribute User user) {
        commentService.delete(commentId, user.getId());
        Post post = postService.findById(postId);
        model.addAttribute(post);
        return "post/detail :: #commentsContent";
    }
}
