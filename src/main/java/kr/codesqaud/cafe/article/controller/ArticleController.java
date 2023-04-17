package kr.codesqaud.cafe.article.controller;

import kr.codesqaud.cafe.article.dto.RequestForm;
import kr.codesqaud.cafe.article.dto.ResponseDetail;
import kr.codesqaud.cafe.article.service.ArticleService;
import kr.codesqaud.cafe.utils.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;


    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }


    @GetMapping("/write")
    public String writeForm(HttpSession session) {
        return Session.isLoggedIn(session) ? "articles/write-form" : "redirect:/user/login";
    }

    //글 작성 클릭 시 매핑하고 글 저장
    @PostMapping("/save")
    public String createArticle(RequestForm requestForm, HttpSession session) {
        articleService.save(requestForm, Session.getUserId(session));
        return "redirect:/articles";
    }

    //인덱스(홈)으로 매핑하여 글 목록을 보여줌
    @GetMapping
    public String listArticles(Model model) {
        model.addAttribute("articles", articleService.getPreviewDtos());
        return "articles/list";
    }

    //글 목록에서 제목 클릭시, 상세 글을 보여줌
    @GetMapping("/{id}")
    public String showArticleDetail(Model model, @PathVariable long id, HttpSession session) {
        model.addAttribute("articleDetail", articleService.getArticleDetail(id));
        return Session.isLoggedIn(session) ? "articles/show-detail" : "redirect:/user/login";
    }

    @GetMapping("/{id}/modify")
    public String showModifyForm(@PathVariable long id, Model model, HttpSession session) {
        ResponseDetail responseDetail = articleService.getArticleDetail(id);
        if (!Session.getUserId(session).equals(responseDetail.getAuthor())) {
            return "articles/forbidden";
        }
        model.addAttribute("articleDetail", articleService.getArticleDetail(id));
        return "articles/modify-form";
    }

    @PutMapping("/{id}")
    public String updateArticle(@PathVariable long id, RequestForm requestForm) {
        articleService.update(id, requestForm);
        return "redirect:/articles/" + id;
    }

    @DeleteMapping("/delete")
    public String deleteArticle(long id, HttpSession session) {
        ResponseDetail responseDetail = articleService.getArticleDetail(id);
        if (!Session.getUserId(session).equals(responseDetail.getAuthor())) {
            return "articles/forbidden";
        }
        articleService.delete(id);
        return "redirect:/articles";
    }


}
