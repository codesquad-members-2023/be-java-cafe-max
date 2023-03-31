package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.ArticleDTO;
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

    @GetMapping("/post/write")
    public String postForm() {
        return "post/form";
    }

    @PostMapping("/post/write")
    public String writePost(@ModelAttribute ArticleDTO articleDto) {
        articleService.write(articleDto);
        return "redirect:/";
    }

    @GetMapping("/post/show/{id}")
    public String showPost(@PathVariable long id, Model model) {
        ArticleDTO wantedPost = articleService.clickOne(id);
        model.addAttribute("wantedPost", wantedPost);
        return "post/show";
    }
}

