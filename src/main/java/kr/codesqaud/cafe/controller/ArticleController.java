package kr.codesqaud.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArticleController {

    @GetMapping("/question")
    public String introPageEqualToArticlePage(){

        return "/qna/form";
    }

}
