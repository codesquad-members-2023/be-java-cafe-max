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

    @PostMapping("/article/write")
    public String write(final ArticleDTO articleDTO) {
        Article article = new Article(articleDTO.getWriter(), articleDTO.getTitle(), articleDTO.getContents());
        articleService.save(article);
        return "redirect:/";
    }

    @GetMapping("/articles/{index}")
    public String viewArticle(@PathVariable final long index, final Model model) {
        Article findArticle = articleService.findOne(index).get();
        model.addAttribute("article", findArticle);
        return "/qna/show";
    }

}
