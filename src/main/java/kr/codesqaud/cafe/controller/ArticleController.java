package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.ArticleCreateDto;
import kr.codesqaud.cafe.controller.dto.ArticleReadDto;
import kr.codesqaud.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/articles")
@Controller
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("article") ArticleCreateDto articleCreateDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "article/form";
        }

        articleService.create(articleCreateDto);

        return "article/show";
    }

    @GetMapping("/{id}")
    public String read(@PathVariable Long id, Model model) {
        final ArticleReadDto articleReadDto = articleService.find(id);

        model.addAttribute("article", articleReadDto);

        return "article/show";
    }
}
