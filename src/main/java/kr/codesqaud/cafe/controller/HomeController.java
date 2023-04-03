package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.ArticleCreateDto;
import kr.codesqaud.cafe.controller.dto.UserJoinDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "forward:/articles";
    }

    @GetMapping("/users/join")
    public String joinUser(Model model) {
        model.addAttribute("user", new UserJoinDto());

        return "user/form";
    }

    @GetMapping("/articles/create")
    public String createArticle(Model model) {
        model.addAttribute("article", new ArticleCreateDto());
        return "article/form";
    }
}
