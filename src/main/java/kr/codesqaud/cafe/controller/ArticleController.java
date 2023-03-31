package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.config.MvcConfig;
import kr.codesqaud.cafe.controller.dto.ArticleForm;
import kr.codesqaud.cafe.domain.Article;
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
    // TODO: mvcConfig 이용해 보기
    private final MvcConfig mvcConfig;

    @Autowired
    public ArticleController(ArticleService articleService, MvcConfig mvcConfig) {
        this.articleService = articleService;
        this.mvcConfig = mvcConfig;
    }

    @GetMapping("/questions/add")
    public String addForm() {
        return "qna/form";
    }

    @PostMapping("/questions/post")
    public String postArticle(ArticleForm form) {
        Article article = new Article(form.getWriter(), form.getTitle(), form.getContents());
        article.setWriter(article.getWriter());

        articleService.add(article);

        return "redirect:/";
    }

    @GetMapping("/")
    public String articleList(Model model) {
        List<Article> articles = articleService.findArticles();
        model.addAttribute("articles", articles);
        return "index";
    }

    @GetMapping("/questions/{articleIndex}")
    public String articleQna(@PathVariable("articleIndex") Long articleIndex, Model model) {
        Article article = articleService.findByArticleIndex(articleIndex);
        model.addAttribute("article", article);
        return "qna/show";
    }

    @GetMapping("/questions/show")
    public String showForm() {
        return "qna/show";
    }
}
