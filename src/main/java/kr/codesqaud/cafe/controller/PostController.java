package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Post;
import kr.codesqaud.cafe.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/post/form")
    public String form() {
        return "post/form";
    }

    @GetMapping("/post/{id}")
    public String show(@PathVariable long id, Model model){
        model.addAttribute("post", postService.findById(id));
        return "post/show";
    }

    @PostMapping("/post/form")
    public String write(PostForm postForm) {
        Post post = new Post.Builder()
                .title(postForm.getTitle())
                .contents(postForm.getContents())
                .writer(postForm.getWriter())
                .build();
        postService.create(post);
        return "redirect:/";
    }
}

