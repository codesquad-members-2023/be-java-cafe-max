package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.article.ArticleForm;
import kr.codesqaud.cafe.controller.dto.article.ArticleTimeForm;
import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/questions/post")
    public String addArticle(@Valid ArticleForm form) {
        articleService.add(form);
        return "redirect:/";
    }

    @GetMapping("/")
    public String addArticles(Model model) {
        // 현재 시작을 가져오는 목적(now 메서드)으로 DTO 사용
        List<ArticleTimeForm> articles = articleService.findArticles();
        model.addAttribute("articles", articles);
        return "index";
    }

    @GetMapping("/questions/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        ArticleTimeForm article = articleService.findArticleId(id);
        model.addAttribute("article", article);
        return "qna/show";
    }
}
