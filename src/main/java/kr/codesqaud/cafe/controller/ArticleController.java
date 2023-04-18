package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.request.PostRequest;
import kr.codesqaud.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/article")
    public String post(@ModelAttribute final PostRequest request) {
        articleService.post(request);
        return "redirect:/";
    }

    @GetMapping("/article")
    public String showArticle(){
        return "qna/form";
    }

    @GetMapping("/articles/{articleId}")
    public String showArticleDetails(@PathVariable final Long articleId, final Model model) {
        model.addAttribute("article", articleService.findById(articleId));
        return "qna/show";
    }
}
