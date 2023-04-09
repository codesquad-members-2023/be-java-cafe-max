package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.ArticleDTO;
import kr.codesqaud.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public String showPostArticleForm(){
        return "post/form";
    }

    @PostMapping
    public String postArticle(@Valid ArticleDTO articleDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "post/form";
        }
        articleService.post(articleDto);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String showDetailArticle(@PathVariable int id, Model model) {
        model.addAttribute("article", articleService.findArticleById(id));
        return "post/show";
    }
}
