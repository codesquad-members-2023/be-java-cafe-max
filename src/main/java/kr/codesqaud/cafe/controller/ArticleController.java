package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.config.SessionAttributeNames;
import kr.codesqaud.cafe.dto.article.ArticleResponse;
import kr.codesqaud.cafe.dto.article.ArticleSaveRequest;
import kr.codesqaud.cafe.dto.article.ArticleUpdateRequest;
import kr.codesqaud.cafe.exception.user.AccessDeniedException;
import kr.codesqaud.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpSession;

@Controller
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public String listAllArticles(Model model) {
        model.addAttribute("articles", articleService.getAllArticles());
        return "article/main";
    }

    @GetMapping("/articles/write")
    public String getFormToWrite() {
        return "article/write";
    }

    @PostMapping("/articles")
    public String writeArticle(@ModelAttribute ArticleSaveRequest articleSaveRequest) {
        articleService.saveArticle(articleSaveRequest);
        return "redirect:/";
    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id, Model model) {
        model.addAttribute("article", articleService.getArticleWithSurrounding(id));
        return "article/detail";
    }

    @GetMapping("/articles/{id}/edit")
    public String getFormToEdit(@PathVariable Long id, Model model, HttpSession httpSession) {
        final ArticleResponse article = articleService.findById(id);
        checkUserPermissions(httpSession, article.getWriter());
        model.addAttribute("article", article);
        return "article/edit";
    }

    @PutMapping("/articles/{id}")
    public String editArticle(@PathVariable Long id, @ModelAttribute ArticleUpdateRequest articleUpdateRequest, HttpSession httpSession) {
        checkUserPermissions(httpSession, articleUpdateRequest.getWriter());
        articleService.editArticle(articleUpdateRequest);
        return "redirect:/articles/" + id;
    }

    @DeleteMapping("/articles/{id}")
    public String deleteArticle(@PathVariable Long id, @ModelAttribute ArticleUpdateRequest articleUpdateRequest, HttpSession httpSession) {
        checkUserPermissions(httpSession, articleUpdateRequest.getWriter());
        articleService.deleteArticle(id);
        return "redirect:/";
    }

    private void checkUserPermissions(HttpSession httpSession, String writer) {
        if (!httpSession.getAttribute(SessionAttributeNames.LOGIN_USER_NAME).equals(writer)) {
            throw new AccessDeniedException();
        }
    }
}
