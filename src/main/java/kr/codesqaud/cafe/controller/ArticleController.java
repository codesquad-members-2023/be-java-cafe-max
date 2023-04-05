package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.article.Article;
import kr.codesqaud.cafe.article.MemoryArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    private final MemoryArticleRepository memoryArticleRepository;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    public ArticleController(MemoryArticleRepository memoryArticleRepository) {
        this.memoryArticleRepository = memoryArticleRepository;
    }

    @PostMapping("/qna/form")
    public String saveArticle(Article article, Model model) {
        memoryArticleRepository.save(article);
        logger.info(article.toString());
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String showNewArticles(Model model) {
        model.addAttribute("articles", memoryArticleRepository.findAll());
        return "index";
    }
}
