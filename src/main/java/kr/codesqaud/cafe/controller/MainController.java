package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class MainController {
    private final ArticleService articleService;

    public MainController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public String showMain(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        model.addAttribute("articles", articleService.getArticlesWithCommentCount());

        if (session == null || session.getAttribute("loginUser") == null) {
            return "index";
        }

        User loginUser = (User) session.getAttribute("loginUser");
        session.setAttribute("isLogin", true);
        model.addAttribute("user", loginUser);
        return "index";
    }

}
