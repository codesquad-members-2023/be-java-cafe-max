package kr.codesqaud.cafe.article.controller;

import kr.codesqaud.cafe.article.dto.RequestArticleWriteForm;
import kr.codesqaud.cafe.article.dto.ResponseArticleDetail;
import kr.codesqaud.cafe.article.service.ArticleService;
import kr.codesqaud.cafe.comment.domain.Comment;
import kr.codesqaud.cafe.comment.service.CommentService;
import kr.codesqaud.cafe.utils.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;
    private final CommentService commentService;


    public ArticleController(ArticleService articleService, CommentService commentService) {
        this.articleService = articleService;
        this.commentService = commentService;
    }


    @GetMapping("/write")
    public String writeForm(HttpSession session) {
        return Session.isLoggedIn(session) ? "articles/write-form" : "redirect:/user/login";
    }

    //글 작성 클릭 시 매핑하고 글 저장
    @PostMapping
    public String createArticle(RequestArticleWriteForm requestArticleWriteForm, HttpSession session) {
        articleService.save(requestArticleWriteForm, Session.getUserId(session));
        return "redirect:/articles";
    }

    //인덱스(홈)으로 매핑하여 글 목록을 보여줌
    @GetMapping
    public String listArticles(@RequestParam(name = "page", defaultValue = "1") int page, Model model) {
        int pageSize = 3;
        model.addAttribute("articles", articleService.findByRange((page - 1) * pageSize, pageSize));
        model.addAttribute("paginationInfo", articleService.getPaginationInfo(page, pageSize));
        return "articles/list";
    }

    //글 목록에서 제목 클릭시, 상세 글을 보여줌
    @GetMapping("/{id}")
    public String showArticleDetail(Model model, @PathVariable long id, HttpSession session) {
        model.addAttribute("articleDetail", articleService.getArticleDetail(id));
        List<Comment> comments = commentService.getCommentsForArticle(id);
        if (!comments.isEmpty()) model.addAttribute("comments", comments);
        return Session.isLoggedIn(session) ? "articles/show-detail" : "redirect:/user/login";
    }


    @GetMapping("/{id}/modify")
    public String showModifyForm(@PathVariable long id, Model model, HttpSession session) {
        ResponseArticleDetail responseArticleDetail = articleService.getArticleDetail(id);
        if (!Session.getUserId(session).equals(responseArticleDetail.getAuthor())) {
            return "articles/forbidden";
        }
        model.addAttribute("articleDetail", articleService.getArticleDetail(id));
        return "articles/modify-form";
    }

    @PutMapping("/{id}")
    public String updateArticle(@PathVariable long id, RequestArticleWriteForm requestArticleWriteForm) {
        articleService.update(id, requestArticleWriteForm);
        return "redirect:/articles/" + id;
    }

    @DeleteMapping("/{id}")
    public String deleteArticle(@PathVariable long id, HttpSession session) {
        ResponseArticleDetail responseArticleDetail = articleService.getArticleDetail(id);
        if (!Session.getUserId(session).equals(responseArticleDetail.getAuthor())) {
            return "articles/forbidden";
        }
        articleService.delete(id);
        return "redirect:/articles";
    }


}
