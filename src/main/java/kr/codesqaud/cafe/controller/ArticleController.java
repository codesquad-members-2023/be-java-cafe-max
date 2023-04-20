package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {
    private final ArticleService articleService;
    public ArticleController(ArticleService articleService){
        this.articleService = articleService;
    }

    @GetMapping("/ask")
    public String getArticleForm(){
        return "/qna/form";
    }
    @PostMapping("/articles")
    public String makeNewArticle(Article article){
        articleService.join(article);
        return "redirect:/articles";
    }
    @GetMapping("/articles")
    public String getArticleList(Model model){
        model.addAttribute("articles", articleService.getArticleList());
        return "index";
    }

    @GetMapping("/articles/{articleNum}")
    public String articleProfile(@PathVariable Long articleNum, Model model){
            model.addAttribute("article", articleService.getArticleByArticleNum(articleNum));
        return "qna/show";
    }
}
