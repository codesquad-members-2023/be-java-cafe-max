package kr.codesqaud.cafe.web.controller;

import kr.codesqaud.cafe.post.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    private final PostService postService;

    public IndexController(PostService postService){
        this.postService = postService;
    }
    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("posts", postService.findPosts());
        return "index";
    }
}
