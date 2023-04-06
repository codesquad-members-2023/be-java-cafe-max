package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.article.ArticleForm;
import kr.codesqaud.cafe.controller.dto.article.ArticleTimeForm;
import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/questions/post")
    // ArticleForm: 이러면 검증을 위한 dto인가??
    public String postArticle(@Validated ArticleForm form) {
        Article article = new Article(form);
        articleService.add(article);
        return "redirect:/";
    }

    @GetMapping("/")
    public String articleList(Model model) {
        List<ArticleTimeForm> articles = articleService.findArticles();
        model.addAttribute("articles", articles);
        return "index";
    }

    @GetMapping("/questions/{id}")
    public String articleQna(@PathVariable("id") Long id, Model model) {
        ArticleTimeForm article = articleService.findArticleId(id);
        model.addAttribute("article", article);
        return "qna/show";
    }
}
