package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    private final ArticleService articleService;

    public MainController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public String showMain(final Model model) {
        model.addAttribute("articles", articleService.getArticles());
        return "index";
    }
}
