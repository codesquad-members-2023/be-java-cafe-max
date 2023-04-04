package codesquad.cafe.controller;

import codesquad.cafe.dto.ArticleRequestDto;
import codesquad.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/questions")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(final ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/form")
    public String showQnaForm() {
        return "qna/form";
    }

    @PostMapping
    public String writePost(@ModelAttribute ArticleRequestDto articleRequestDto) {
        articleService.createPost(articleRequestDto);
        return "redirect:/";
    }
}
