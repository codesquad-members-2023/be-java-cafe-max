package kr.codesquad.cafe.post.controller;

import kr.codesquad.cafe.post.Post;
import kr.codesquad.cafe.post.PostService;
import kr.codesquad.cafe.post.annotation.ValidPostIdPath;
import kr.codesquad.cafe.post.dto.PostForm;
import kr.codesquad.cafe.user.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class PostController {

    public static final String POST_ID = "postId";
    private final PostService postService;


    public PostController(PostService postService) {
        this.postService = postService;
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
    public String viewPost(@PathVariable(POST_ID) Post post, Model model) {
        model.addAttribute(post);
        return "post/detail";
    }

    @ValidPostIdPath
    @GetMapping("/posts/{postId}/edit")
    public String viewEditPost(@PathVariable(POST_ID) Post post, Model model) {
        model.addAttribute(PostForm.from(post));
        model.addAttribute(POST_ID, post.getId());
        return "post/editForm";
    }

    @PutMapping("/posts/{postId}")
    public String editPost(@Valid PostForm postForm, BindingResult bindingResult, @PathVariable long postId, @SessionAttribute User user, Model model) {
        if (bindingResult.hasErrors()) {
            return "post/editForm";
        }
        Post editPost = postService.updateFromPostForm(postId, postForm, user.getId());
        model.addAttribute(editPost);
        return "post/detail";
    }

    @DeleteMapping("/posts/{postId}")
    public String deletePost(@PathVariable long postId, @SessionAttribute User user) {
        postService.delete(postId, user.getId());
        return "redirect:/";
    }
}
