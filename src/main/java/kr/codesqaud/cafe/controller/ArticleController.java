package kr.codesqaud.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    @PostMapping("")
    public String writeArticle(ArticleDto articleDto) {
        return "redirect:/";
    }
}
