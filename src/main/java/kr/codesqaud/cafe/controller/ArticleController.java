package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.dto.ArticlePostRequest;
import kr.codesqaud.cafe.service.ArticleService;
import kr.codesqaud.cafe.dto.ArticleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ArticleController {
    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @RequestMapping("/article/write")
    public String create(
            HttpServletRequest request,
            @ModelAttribute("question") ArticlePostRequest articlePostRequest,
            Model model
            ){
        if("POST".equals(request.getMethod())) {
            articleService.post(articlePostRequest);
            return "redirect:/";
        }

        return "qna/form";
    }

    @GetMapping("/")
    public String showIndex(Model model){
        List<ArticleResponse> articles = articleService.findArticles();
        model.addAttribute("articles", articles);

        return "index";
    }

    @GetMapping("/article/{articleId}")
    public String showDetail(@PathVariable("articleId") long articleId, Model model){
        model.addAttribute("article", articleService.findByArticleId(articleId));

        return "qna/show";
    }
}
