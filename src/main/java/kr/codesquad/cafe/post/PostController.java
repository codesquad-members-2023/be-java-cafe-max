package kr.codesquad.cafe.post;

import kr.codesquad.cafe.comment.Comment;
import kr.codesquad.cafe.comment.CommentService;
import kr.codesquad.cafe.post.annotation.ValidPostIdPath;
import kr.codesquad.cafe.post.dto.PostForm;
import kr.codesquad.cafe.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class PostController {

    private final PostService postService;

    private final CommentService commentService;

    public PostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping(value = {"/posts/new", "/posts/form"})
    public String viewPostForm(@ModelAttribute PostForm postForm) {
        return "post/form";
    }

    @PostMapping("/posts")
    public String savePost(@Valid PostForm postForm, Errors errors, @SessionAttribute User user) {
        if (errors.hasErrors()) {
            return "post/form";
        }
        Post post = postService.save(postForm, user);
        return "redirect:/posts/" + post.getId();
    }

    @GetMapping("/posts/{postId}")
    public String viewPost(@PathVariable("postId") Post post, Model model) {
        model.addAttribute(post);
        return "post/detail";
    }

    @ValidPostIdPath
    @GetMapping("/posts/{postId}/edit")
    public String viewEditPost(@PathVariable("postId") Post post, Model model) {
        model.addAttribute(PostForm.from(post));
        model.addAttribute(post.getId());
        return "post/editForm";
    }

    @ValidPostIdPath
    @PutMapping("/posts/{postId}")
    public String editPost(@Valid PostForm postForm, BindingResult bindingResult, @PathVariable("postId") Post post, Model model) {
        if (bindingResult.hasErrors()) {
            return "post/editForm";
        }
        Post editPost = postService.updateFromPostForm(post, postForm);
        model.addAttribute(editPost);
        return "post/detail";
    }

    @ValidPostIdPath
    @DeleteMapping("/posts/{postId}")
    public String deletePost(@PathVariable("postId") Post post) {
        postService.delete(post);
        return "redirect:/";
    }

    @PostMapping("/posts/{postId}/comments")
    public String addComment(@RequestParam("commentText") String content,@PathVariable("postId") Post post, @SessionAttribute User user) {
        Comment comment = commentService.from(content, post, user);
        postService.save(post, comment);
        return "redirect:/posts/{postId}";
    }
}
