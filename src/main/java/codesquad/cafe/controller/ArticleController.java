package codesquad.cafe.controller;

import codesquad.cafe.dto.ArticleRequestDto;
import codesquad.cafe.dto.ArticleResponseDto;
import codesquad.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(final ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/")
    public String showHome(Model model) {
        List<ArticleResponseDto> posts = articleService.findPosts();
        model.addAttribute("posts", posts);
        return "index";
    }

    @GetMapping("/questions/form")
    public String showQnaForm() {
        return "qna/form";
    }

    @PostMapping("/questions")
    public String writePost(@ModelAttribute ArticleRequestDto articleRequestDto) {
        articleService.createPost(articleRequestDto);
        return "redirect:/";
    }

}
