package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {
    private final ArticleService articleService;
    
    public ArticleController(ArticleService articleService){
        this.articleService = articleService;
    }

    @GetMapping("/question")
    public String introPageEqualToArticlePage(){
        return "/qna/form";
    }
    @PostMapping("/articles")
    public String makeNewArticle(Article article){
        articleService.join(article);
        return "redirect:/articles";
    }
    @GetMapping("/articles")
    public String showArticles(){
        return "/qna/show";
    }
}
