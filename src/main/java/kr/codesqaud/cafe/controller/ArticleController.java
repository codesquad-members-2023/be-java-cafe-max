package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ArticleController {
    private final ArticleService articleService;
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }
    @GetMapping("/questions")
    public String createForm(){
        return "qna/form";
    }

    @PostMapping("/questions")
    public String create(ArticleForm form){
        Article article = new Article();
        article.setWriter(form.getWriter());
        article.setTitle(form.getTitle());
        article.setContents(form.getContents());
        article.setCreatedAt(LocalDateTime.now());
        article.setPoints(1L);

        articleService.post(article);
        return "redirect:/";
    }

    @GetMapping("/")
    public String list(Model model){
        List<Article> articles = articleService.findArticles();
        model.addAttribute("articles", articles);
        return "/index";
    }

    @GetMapping("/articles/{id}")
    public String findArticle(@PathVariable Long id, Model model){
        Article article = articleService.findOne(id).get();
        model.addAttribute("article", article);
        return "qna/show";
    }
}
