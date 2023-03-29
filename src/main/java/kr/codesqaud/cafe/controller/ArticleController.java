package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.DTO.ArticleDTO;
import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/article/write")
    public String articlePage() {
        return "/qna/form";
    }

    @PostMapping("/article/write")
    public String write(ArticleDTO articleDTO) {
        Article article = new Article();
        article.setWriter(articleDTO.getWriter());
        article.setTitle(articleDTO.getTitle());
        article.setContents(articleDTO.getContents());
        articleService.save(article);
        return "redirect:/";
    }

    @GetMapping("/articles/{index}")
    public String viewArticle(@PathVariable long index, Model model) {
        Article findArticle = articleService.findOne(index).get();
        model.addAttribute("writer", findArticle.getWriter());
        model.addAttribute("title", findArticle.getTitle());
        model.addAttribute("contents", findArticle.getContents());
        return "/qna/show";
    }

}
