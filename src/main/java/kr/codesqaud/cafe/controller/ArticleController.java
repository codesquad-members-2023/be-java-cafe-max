package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.dto.ArticleForm;
import kr.codesqaud.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping
public class ArticleController {
    ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public String showIndex(Model model) {
        List<Article> articles = articleService.findAll();
        model.addAttribute("articles", articles);

        return "index";
    }

    @GetMapping("/write")
    public String showWrite(ArticleForm articleForm) {
        return "qna/form";
    }

    @PostMapping("/write")
    public String write(ArticleForm articleForm) {
        articleService.saveArticle(articleForm);

        return "redirect:/";
    }

    @GetMapping("/{index}")
    public String findByIndex(@PathVariable("index") int index, Model model) {
        Article article = articleService.findArticleIndexOf(index - 1);
        model.addAttribute("article", article);

        return "qna/show";
    }

}
