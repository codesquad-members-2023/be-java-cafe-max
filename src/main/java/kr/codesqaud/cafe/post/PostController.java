package kr.codesqaud.cafe.post;

import kr.codesqaud.cafe.post.dto.PostForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(value = {"/posts/new", "/posts/form"})
    public String viewPostForm(@ModelAttribute PostForm postForm) {
        return "/post/form";
    }

    @PostMapping("/posts")
    public String savePost(@Valid PostForm postForm, Errors errors) {
        if (errors.hasErrors()) {
            return "/post/form";
        }
        long postId = postService.save(postForm);
        return "redirect:/posts/" + postId;
    }

    @GetMapping("/posts/{postId}")
    public String viewPost(Model model, @PathVariable int postId) {
        Post post = postService.findById(postId);
        model.addAttribute(post);
        return "/post/detail";
    }
}
