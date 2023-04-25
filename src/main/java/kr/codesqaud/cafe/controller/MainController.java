package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private final PostService postService;

    public MainController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public String showMain(Model model) {
        model.addAttribute("postResponses", postService.findAll());
        return "index";
    }
}
