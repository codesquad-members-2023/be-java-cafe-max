package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.ArticleForm;
import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/questions/post")
    public String postArticle(ArticleForm form) {
        Article article = new Article(form.getWriter(), form.getTitle(), form.getContents());

        articleService.add(article);

        return "redirect:/";
    }

    @GetMapping("/")
    public String articleList(Model model) {
        List<Article> articles = articleService.findArticles();
        model.addAttribute("articles", articles);
        return "index";
    }

    @GetMapping("/questions/{id}")
    public String articleQna(@PathVariable("id") Long id, Model model) {
        Article article;
        if (articleService.findById(id).isPresent()) {
            article = articleService.findById(id).get();
        } else {
            throw new NoSuchElementException();
        }
        model.addAttribute("article", article);
        return "qna/show";
    }
}
