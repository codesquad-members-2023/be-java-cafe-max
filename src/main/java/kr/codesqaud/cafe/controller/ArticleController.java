package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.ArticleDto;
import kr.codesqaud.cafe.controller.dto.request.articleRequest.PostEditRequest;
import kr.codesqaud.cafe.controller.dto.request.articleRequest.PostRequest;
import kr.codesqaud.cafe.controller.modelBuilder.ModelBuilder;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.service.ArticleService;
import kr.codesqaud.cafe.service.ReplyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ArticleController {
    private final ArticleService articleService;
    private final ReplyService replyService;
    private final ModelBuilder modelBuilder;

    public ArticleController(ArticleService articleService, ReplyService replyService, ModelBuilder modelBuilder) {
        this.articleService = articleService;
        this.replyService = replyService;
        this.modelBuilder = modelBuilder;
    }

    @PostMapping("/article")
    public String postArticle(@ModelAttribute final PostRequest request, HttpServletRequest httpRequest) {
        articleService.post(request, httpRequest);
        return "redirect:/";
    }

    @GetMapping("/article-post-form")
    public String showArticlePostForm() {
        return "qna/form";
    }

    @GetMapping("/articles/{articleId}")
    public String showArticleDetails(@PathVariable final Long articleId, final Model model) {
        model.addAttribute("article", articleService.findById(articleId));
        model.addAttribute("reply", replyService.getReplies(articleId));
        model.addAttribute("replyCount", replyService.getReplyCount(articleId));
        return "qna/show";
    }

    @GetMapping("/article-edit-form/{articleId}")
    public String showArticleEditForm(@PathVariable final Long articleId, HttpServletRequest httpRequest, Model model) {
        HttpSession session = httpRequest.getSession(false);
        ArticleDto article = articleService.findById(articleId);

        model.addAttribute("title", article.getTitle());
        model.addAttribute("content", article.getContent());

        if (session != null) {
            User loginUser = (User) session.getAttribute("loginUser");
            if (loginUser != null && loginUser.getUserId().equals(article.getWriter())) {
                return "qna/edit_form";
            }
        }
        modelBuilder.showFailed(articleId, model);
        return "qna/failed";
    }

    @PutMapping("/articles/{articleId}")
    public String editArticle(@PathVariable final Long articleId, @ModelAttribute final PostEditRequest request) {
        articleService.editArticle(articleId, request);
        return "redirect:/articles/{articleId}";
    }

    @DeleteMapping("/articles/{articleId}")
    public String deleteArticle(@PathVariable final Long articleId, HttpServletRequest httpRequest, Model model) {
        HttpSession session = httpRequest.getSession(false);
        ArticleDto article = articleService.findById(articleId);
        model.addAttribute("article", article);
        if (session != null) {
            User loginUser = (User) session.getAttribute("loginUser");
            if (loginUser != null && loginUser.getUserId().equals(article.getWriter())) {
                articleService.deleteArticle(articleId);
                return "redirect:/";
            }
        }
        modelBuilder.showFailed(articleId, model);
        return "qna/failed";
    }
}
