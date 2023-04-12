package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.article.ArticleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final ArticleRepository articleRepository;

    public HomeController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping(value = {"/", "/index"})
    public String home(Model model) {
        model.addAttribute("articles", articleRepository.findAll());
        return "index";
    }
}
