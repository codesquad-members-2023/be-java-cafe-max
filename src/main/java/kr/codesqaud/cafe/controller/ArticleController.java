package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.article.Article;
import kr.codesqaud.cafe.article.ArticleDTO;
import kr.codesqaud.cafe.article.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigInteger;
import java.util.List;

@Controller
public class ArticleController {

    private final ArticleRepository articleRepository;
    private final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @PostMapping("/qna/form")
    public String saveArticle(Article article) {
        articleRepository.save(article);
        return "redirect:/index";
    }

    @GetMapping("/articles/{articleId}")
    public String showDetail(@PathVariable BigInteger articleId, Model model) {
        ArticleDTO articleDTO = articleRepository.findById(articleId);
        model.addAttribute("article", articleDTO);
        return "qna/detail";
    }
}
