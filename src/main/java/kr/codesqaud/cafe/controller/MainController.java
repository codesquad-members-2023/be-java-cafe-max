package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.service.ArticleService;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    private final UserService userService;
    private final ArticleService articleService;

    public MainController(UserService userService, ArticleService articleService) {
        this.userService = userService;
        this.articleService = articleService;
    }

    @GetMapping
    public String showMain(@CookieValue(name = "id", required = false) Long id, Model model) {
        model.addAttribute("articles", articleService.getArticles());
        if (id == null) {
            return "index";
        }
        User loginUser = userService.findById(id);
        if (loginUser == null) {
            return "index";
        }
        model.addAttribute("user", loginUser);
        return "login_index";
    }
}
