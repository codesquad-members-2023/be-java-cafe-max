package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.article.ArticleDTO;
import kr.codesqaud.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final ArticleService articleService;

    public HomeController(final ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/")
    public String home(final Model model) {
        List<ArticleDTO> allPosts = articleService.gatherPosts();
        model.addAttribute("allPosts", allPosts);
        return "index";
    }
}
