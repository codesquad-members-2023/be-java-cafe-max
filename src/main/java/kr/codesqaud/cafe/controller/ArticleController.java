package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ArticleController {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping
    public String listAllArticles(Model model) {
        List<Article> articles = articleRepository.findAll();
        model.addAttribute("articles", articles);
        return "article/main";
    }

    @GetMapping("/articles/write")
    public String write() {
        return "article/form";
    }

    @PostMapping("/articles/write")
    public String write(@ModelAttribute Article article) {
        articleRepository.save(article);
        return "redirect:/";
    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable("id") Long id, Model model) {
        Article article = articleRepository.findById(id);
        model.addAttribute("article", article);
        return "article/show";
    }
}
