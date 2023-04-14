package kr.codesqaud.cafe.controller.article;

import kr.codesqaud.cafe.domain.dto.article.ArticleForm;
import kr.codesqaud.cafe.domain.dto.article.ArticleTimeForm;
import kr.codesqaud.cafe.domain.dto.article.ArticleUpdateForm;
import kr.codesqaud.cafe.service.article.ArticleService;
import kr.codesqaud.cafe.session.SessionConst;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/questions/add")
    public String join(@ModelAttribute ArticleForm articleForm) {
        return "qna/form";
    }

    @PostMapping("/questions/post")
    public String addArticle(@Valid ArticleForm articleForm, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "qna/form";
        }

        // 서비스에서 DTO 사용으로 User에 넣어줄 필요가 없어짐
        articleService.add(articleForm, session);
        return "redirect:/";
    }

    @GetMapping("/")
    public String addArticles(Model model) {
        // 현재 시작을 가져오는 목적(now 메서드)으로 DTO 사용
        List<ArticleTimeForm> articles = articleService.findArticles();
        model.addAttribute("articles", articles);
        return "index";
    }

    @GetMapping("/questions/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        ArticleTimeForm article = articleService.findArticleId(id);
        model.addAttribute("article", article);
        return "qna/show";
    }

    @GetMapping("/questions/{id}/updateForm")
    public String getUpdateArticle(@PathVariable Long id, Model model, HttpSession session) {
        validateUserId(id, session);
        ArticleUpdateForm article = articleService.findUpdate(id);
        model.addAttribute("articleUpdated", article);
        return "qna/updateForm";
    }

    @PutMapping("/questions/{id}")
    public String putUpdateArticle(@PathVariable Long id, @Valid ArticleUpdateForm updateArticle) {
        articleService.updateArticle(id, updateArticle);
        return "redirect:/";
    }

    @DeleteMapping("/questions/{id}")
    public String deleteArticle(@PathVariable Long id, HttpSession session) {
        validateUserId(id, session);
        articleService.delete(id);
        return "redirect:/";
    }

    private void validateUserId(Long id, HttpSession session) {
        String loginUserId = (String) session.getAttribute(SessionConst.LOGIN_USER_ID);
        ArticleTimeForm article = articleService.findArticleId(id);

        if (!loginUserId.equals(article.getUserId())) {
            throw new IllegalArgumentException("자신이 작성한 게시물이어야 합니다.");
        }
    }
}
