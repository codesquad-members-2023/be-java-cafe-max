package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.ArticleMemoryRepository;
import kr.codesqaud.cafe.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ArticleController {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleController(ArticleMemoryRepository articleMemoryRepository) {
        this.articleRepository = articleMemoryRepository;
    }

    @RequestMapping("/qna/write")
    public String create(
            HttpServletRequest request,
            @ModelAttribute Article article
    ){
        if("POST".equals(request.getMethod())) {
            articleRepository.save(article);
            return "redirect:/";
        }

        return "qna/form";
    }

    @GetMapping("/")
    public String showIndex(Model model){
        model.addAttribute("articles", articleRepository.findAll());

        return "index";
    }

    @GetMapping("/articles/{articleId}")
    public String showDetail(@PathVariable("articleId") long articleId, Model model){
        model.addAttribute("article", articleRepository.findByArticleId(articleId));

        return "qna/show";
    }
}
