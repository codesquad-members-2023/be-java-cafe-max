package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.article.ArticleCreateDto;
import kr.codesqaud.cafe.controller.dto.article.ArticleReadDto;
import kr.codesqaud.cafe.controller.dto.article.ArticleUpdateDto;
import kr.codesqaud.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.validation.Valid;

@Controller
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping({"/", "/articles"})
    public String readArticles(Model model) {
        model.addAttribute("articles", articleService.findALl());

        return "home";
    }

    @GetMapping("/articles/create-form")
    public String createArticle(Model model) {
        model.addAttribute("article", new ArticleCreateDto());
        return "article/form";
    }

    @PostMapping("/articles")
    public String create(@Valid @ModelAttribute("article") ArticleCreateDto articleCreateDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "article/form";
        }

        articleService.create(articleCreateDto);

        return "redirect:/articles";
    }

    @GetMapping("/articles/{id}")
    public String read(@PathVariable Long id, Model model) {
        final ArticleReadDto articleReadDto = articleService.find(id);

        model.addAttribute("article", articleReadDto);

        return "article/show";
    }

    @GetMapping("/articles/{id}/update-form")
    public String updateArticleForm(@PathVariable Long id, Model model) {
        model.addAttribute("article", new ArticleUpdateDto(articleService.find(id)));

        return "article/update";
    }

    @PutMapping("/articles/{id}")
    public String updateArticle(@ModelAttribute("article") ArticleUpdateDto articleUpdateDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "article/update";
        }

        articleService.update(articleUpdateDto);

        return "redirect:/articles";
    }
}
