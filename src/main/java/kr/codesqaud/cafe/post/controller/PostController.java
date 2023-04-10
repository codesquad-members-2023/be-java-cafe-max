package kr.codesqaud.cafe.post.controller;

import kr.codesqaud.cafe.post.controller.request.PostWriteRequest;
import kr.codesqaud.cafe.post.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/write")
    public String writePost() {
        return "post/form";
    }

    @PostMapping("/write")
    public String writePost(PostWriteRequest postWriteRequest) {
        postService.writePost(postWriteRequest);
        return "redirect:";
    }

    @GetMapping("")
    public String showPostList(Model model) {
        postService.showPostList(model);
        return "index";
    }

    @GetMapping("/{id}")
    public String showPost(@PathVariable("id") long id, Model model) {
        postService.showPost(id, model);
        return "post/show";
    }
}
