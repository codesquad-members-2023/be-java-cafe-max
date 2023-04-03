package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.article.Article;
import kr.codesqaud.cafe.article.MemoryArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ArticleController {

    private final MemoryArticleRepository memoryArticleRepository;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    public ArticleController(MemoryArticleRepository memoryArticleRepository) {
        this.memoryArticleRepository = memoryArticleRepository;
    }

    // 405 에러 발생
    @PostMapping("/qna/form")
    public String saveArticle(@RequestParam("writer") String writer,
                              @RequestParam String title,
                              @RequestParam String contents) {

        Article article = new Article(writer, title, contents);
        memoryArticleRepository.save(article);
        logger.info(article.toString());

        return "redirect:/";
    }
}
