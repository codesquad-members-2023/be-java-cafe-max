package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.dto.ArticleDto;
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
        List<ArticleDto> articleDtos = articleService.findAll();
        model.addAttribute("articleDtos", articleDtos);

        return "index";
    }

    @GetMapping("/write")
    public String showWriteForm(ArticleForm articleForm) {
        return "qna/form";
    }

    @PostMapping("/write")
    public String write(ArticleForm articleForm) {
        articleService.saveArticle(articleForm);

        return "redirect:/";
    }

    @GetMapping("/articles/{id}")
    public String findById(@PathVariable("id") long id, Model model) {
        ArticleDto articleDto = articleService.findById(id);
        model.addAttribute("articleDto", articleDto);

        return "qna/show";
    }

}
