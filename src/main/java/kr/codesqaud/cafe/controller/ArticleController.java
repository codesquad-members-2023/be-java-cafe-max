package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.dto.article.ArticleSaveRequest;
import kr.codesqaud.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public String listAllArticles(Model model) {
        model.addAttribute("articles", articleService.getAllArticles());
        return "article/main";
    }

    @GetMapping("/articles/write")
    public String write() {
        return "article/write";
    }

    @PostMapping("/articles")
    public String write(@ModelAttribute ArticleSaveRequest articleSaveRequest) {
        articleService.saveArticle(articleSaveRequest);
        return "redirect:/";
    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id, Model model) {
        model.addAttribute("article", articleService.getArticleWithSurrounding(id));
        return "article/detail";
    }
}
