package kr.codesqaud.cafe.web.controller;

import kr.codesqaud.cafe.web.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final ArticleService articleService;

    @Autowired
    public HomeController(ArticleService articleService) {
        this.articleService = articleService;
    }

    // 전체 게시글 조회
    @GetMapping({"/", "/qna"})
    public String home(Model model) {
        model.addAttribute("articles", articleService.getAllArticles());
        return "index";
    }
}
