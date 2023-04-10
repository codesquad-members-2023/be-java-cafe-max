package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.ArticleDTO;
import kr.codesqaud.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/submit")
    public String postArticle(@ModelAttribute @Valid ArticleDTO articleDto) {
        articleService.post(articleDto);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String showDetailArticle(@PathVariable int id, Model model) {
        model.addAttribute("article", articleService.findArticleById(id));
        return "post/show";
    }
}
