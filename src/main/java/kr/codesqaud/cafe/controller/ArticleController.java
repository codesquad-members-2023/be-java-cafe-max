package kr.codesqaud.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    @GetMapping("/question")
    public String introPageEqualToArticlePage(){
        return "/qna/form";
    }
    @PostMapping("/articles")
    public String makeNewArticle(){

        return "redirect:/articles";
    }
    @GetMapping("/articles")
    public String showArticles(){
        return "/qna/show";
    }
}
