package kr.codesqaud.cafe.controller.article;

import kr.codesqaud.cafe.domain.dto.article.ArticleForm;
import kr.codesqaud.cafe.domain.dto.article.ArticleTimeForm;
import kr.codesqaud.cafe.domain.dto.article.ArticleUpdateForm;
import kr.codesqaud.cafe.domain.vo.PageForm;
import kr.codesqaud.cafe.service.article.ArticleService;
import kr.codesqaud.cafe.service.reply.ReplyService;
import kr.codesqaud.cafe.session.SessionConst;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class ArticleController {
    private final ArticleService articleService;
    private final ReplyService replyService;

    public ArticleController(ArticleService articleService, ReplyService replyService) {
        this.articleService = articleService;
        this.replyService = replyService;
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
        articleService.add(articleForm, (String) session.getAttribute(SessionConst.LOGIN_USER_ID));
        return "redirect:/";
    }

    @GetMapping("/")
    public String addArticles(@RequestParam(value = "nowPage", required = false) Integer nowPage, Model model) {
        if (nowPage == null) {
            nowPage = 1;
        }

        PageForm pageForm = new PageForm(nowPage, articleService.countArticles());
        model.addAttribute("articles", articleService.findArticles(pageForm));
        model.addAttribute("pageForm", pageForm);
        return "index";
    }

    @GetMapping("/questions/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("article", articleService.findArticleId(id));
        model.addAttribute("replies", replyService.findReplies(id));
        return "qna/show";
    }

    @GetMapping("/questions/{id}/updateForm")
    public String getUpdateArticle(@PathVariable Long id, Model model, HttpSession session) {
        validateUserId(id, (String) session.getAttribute(SessionConst.LOGIN_USER_ID));
        model.addAttribute("articleUpdated", articleService.findUpdate(id));
        return "qna/updateForm";
    }

    @PutMapping("/questions/{id}")
    public String putUpdateArticle(@PathVariable Long id, @Valid ArticleUpdateForm articleUpdated, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("articleUpdated", articleUpdated);
            return "qna/updateForm";
        }

        articleService.updateArticle(id, articleUpdated);
        return "redirect:/";
    }

    @DeleteMapping("/questions/{id}")
    public String deleteArticle(@PathVariable Long id, HttpSession session) {
        validateUserId(id, (String) session.getAttribute(SessionConst.LOGIN_USER_ID));

        articleService.delete(id);
        return "redirect:/";
    }

    private void validateUserId(Long id, String loginUserId) {
        ArticleTimeForm article = articleService.findArticleId(id);

        if (!loginUserId.equals(article.getUserId())) {
            throw new IllegalArgumentException("자신이 작성한 게시물이어야 합니다.");
        }
    }
}
