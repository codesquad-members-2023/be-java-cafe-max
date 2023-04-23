package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.ArticleDto;
import kr.codesqaud.cafe.controller.dto.request.PostEditRequest;
import kr.codesqaud.cafe.controller.dto.request.PostRequest;
import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/article")
    public String postArticle(@ModelAttribute final PostRequest request, HttpServletRequest httpRequest) {
        articleService.post(request, httpRequest);
        return "redirect:/";
    }

    @GetMapping("/article")
    public String showArticle() {
        return "qna/form";
    }

    @GetMapping("/articles/{articleId}")
    public String showArticleDetails(@PathVariable final Long articleId, final Model model) {
        model.addAttribute("article", articleService.findById(articleId));
        return "qna/show";
    }

    @GetMapping("/articles/edit/{articleId}")
    public String showArticleEditForm(@PathVariable final Long articleId, HttpServletRequest httpRequest, Model model) {
        HttpSession session = httpRequest.getSession(false);
        ArticleDto article = articleService.findById(articleId);

        model.addAttribute("article", article);
        model.addAttribute("title", article.getTitle());
        model.addAttribute("content", article.getContent());

        if (session != null && session.getAttribute("userId").equals(article.getWriter())) {
            return "qna/edit_form";
        }

        return "qna/failed";
    }

    @PutMapping("/articles/{articleId}")
    public String editArticle(@PathVariable final Long articleId, @ModelAttribute final PostEditRequest request, Model model) {
        Article article = articleService.editArticle(articleId, request);
        model.addAttribute("article", article);
        return "qna/show";
    }

    @RequestMapping(value = "/articles/delete/{articleId}", method = {RequestMethod.GET, RequestMethod.DELETE})
    public String deleteArticle(@PathVariable final Long articleId, HttpServletRequest httpRequest, Model model) {
        HttpSession session = httpRequest.getSession(false);
        ArticleDto article = articleService.findById(articleId);
        model.addAttribute("article", article);
        if(session == null) return "qna/failed";
        else if (session.getAttribute("userId").equals(article.getWriter())) {
            articleService.deleteArticle(articleId);
            return "redirect:/";
        }

        return "qna/failed";
    }

}
