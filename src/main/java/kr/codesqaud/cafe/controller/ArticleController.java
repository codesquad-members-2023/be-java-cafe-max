package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.dto.ArticleDto;
import kr.codesqaud.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    private ArticleService articleService;

    public ArticleController() {
        this.articleService = new ArticleService();
    }

    @PostMapping("/post/submit")
    public String postArticle(ArticleDto articleDto) {
        articleService.post(articleDto);
        return "redirect:/";
    }


}
