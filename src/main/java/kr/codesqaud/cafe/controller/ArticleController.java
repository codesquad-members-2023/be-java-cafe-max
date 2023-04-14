package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.dto.ArticlePostRequest;
import kr.codesqaud.cafe.domain.entity.Article;
import kr.codesqaud.cafe.repository.ArticleH2Repository;
import kr.codesqaud.cafe.repository.ArticleRepository;
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

    private final ArticleRepository articleRepository;
    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleH2Repository articleH2Repository, ArticleService articleService) {
        this.articleRepository = articleH2Repository;
        this.articleService = articleService;
    }

    @RequestMapping("/article/write")
    public String create(
            HttpServletRequest request,
            @ModelAttribute("question") ArticlePostRequest articlePostRequest,
            Model model
            ){
        if("POST".equals(request.getMethod())) {
            //TODO Dto -> Entity 메서드
            articleRepository.save(new Article(
                    articlePostRequest.getWriter(),
                    articlePostRequest.getTitle(),
                    articlePostRequest.getContents()
            ));
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
        model.addAttribute("article", articleRepository.findByArticleId(articleId));

        return "qna/show";
    }
}
