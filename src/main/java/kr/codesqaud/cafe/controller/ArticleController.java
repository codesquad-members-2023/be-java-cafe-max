package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.dto.ArticleRequestDto;
import kr.codesqaud.cafe.dto.ArticleResponseDto;
import kr.codesqaud.cafe.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/qna")
    public String writeArticle(final ArticleRequestDto articleRequestDto) {
        articleService.saveArticle(articleRequestDto);
        return "redirect:/";
    }

    @GetMapping
    public String showArticleList(final Model model) {
        List<ArticleResponseDto> articles = articleService.findArticles();
        model.addAttribute("articles", articles);
        return "index";
    }

    @GetMapping("articles/{index}")
    public String findArticleById(@PathVariable Integer index, final Model model) {
        ArticleResponseDto articleResponseDto = articleService.findArticleBySequence(index);
        model.addAttribute("article", articleResponseDto);
        return "qna/show";
    }


}
