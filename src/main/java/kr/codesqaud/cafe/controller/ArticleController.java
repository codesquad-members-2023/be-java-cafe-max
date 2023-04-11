package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.article.Article;
import kr.codesqaud.cafe.article.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    private final ArticleRepository articleRepository;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @PostMapping("/qna/form")
    public String saveArticle(Article article) {
        articleRepository.save(article);
        logger.info(article.toString());
        return "redirect:/index";
    }

    @GetMapping("/articles/{index}")
    public String showDetail(@PathVariable int index, Model model) {
        Article article = articleRepository.findById(index);
        model.addAttribute("article", article);
        return "qna/detail";
    }
}
