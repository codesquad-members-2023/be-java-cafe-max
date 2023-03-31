package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.dto.PostWriteRequest;
import kr.codesqaud.cafe.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/qna/write")
    public String writePost() {
        return "qna/form";
    }

    @PostMapping("/qna/write")
    public String writePost(PostWriteRequest postWriteRequest) {
        postService.writePost(postWriteRequest);
        return "redirect:/";
    }

    @GetMapping("/")
    public String showPostList(Model model) {
        postService.showPostList(model);
        return "index";
    }

    @GetMapping("/qna/{id}")
    public String showPost(@PathVariable("id") long id, Model model) {
        postService.showPost(id, model);
        return "qna/show";
    }
}
