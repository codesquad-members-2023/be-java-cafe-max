package kr.codesqaud.cafe.post;

import kr.codesqaud.cafe.account.User;
import kr.codesqaud.cafe.post.dto.PostForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class PostController {

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
    public String viewPost(@PathVariable int postId, Model model) {
        Post post = postService.findById(postId);
        model.addAttribute(post);
        return "post/detail";
    }

    @GetMapping("/posts/{postId}/edit")
    public String viewEditPost(@PathVariable int postId, @SessionAttribute User user, Model model) {
        Post post = postService.findById(postId);
        postService.checkCanAccess(post.getUser(), user.getId());
        model.addAttribute(PostForm.from(post));
        model.addAttribute(postId);
        return "post/editForm";
    }

    @PutMapping("/posts/{postId}")
    public String editPost(@Valid PostForm postForm, BindingResult bindingResult, @PathVariable int postId, @SessionAttribute User user, Model model) {
        if (bindingResult.hasErrors()) {
            return "post/editForm";
        }
        Post post = postService.findById(postId);
        postService.checkCanAccess(post.getUser(), user.getId());
        Post editPost = postService.updateFromPostForm(post, postForm);
        model.addAttribute(editPost);
        return "post/detail";
    }

    @DeleteMapping("/posts/{postId}")
    public String deletePost(@PathVariable int postId, @SessionAttribute User user) {
        Post post = postService.findById(postId);
        postService.checkCanAccess(post.getUser(), user.getId());
        postService.delete(post);
        return "redirect:/";
    }
}
