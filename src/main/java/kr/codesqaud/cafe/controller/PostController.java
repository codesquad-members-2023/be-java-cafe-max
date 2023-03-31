package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.dto.PostWriteRequest;
import kr.codesqaud.cafe.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/qna")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/write")
    public String writePost() {
        return "qna/form";
    }

    @PostMapping("/write")
    public String writePost(PostWriteRequest postWriteRequest) {
        postService.writePost(postWriteRequest);
        return "redirect:/";
    }
}
