package kr.codesqaud.cafe.controller.article;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.Reply;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.service.ArticleService;
import kr.codesqaud.cafe.service.ReplyService;
import kr.codesqaud.cafe.util.SessionConst;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ArticleController {
    private final ArticleService articleService;
    private final ReplyService replyService;

    public ArticleController(ArticleService articleService, ReplyService replyService)
    {
        this.articleService = articleService;
        this.replyService = replyService;
    }

    // 게시글 목록 보기
    @GetMapping
    public String list(Model model){
        List<Article> articles = articleService.findArticles();
        model.addAttribute("articles", articles);
        return "index";
    }

    // 게시글 작성 페이지로 이동
    @GetMapping("/questions")
    public String create(){
        return "qna/form";
    }

    // 게시글 작성 페이지. 세션을 이용해서 글쓴이 부분을 userId로 할당.
    @PostMapping("/questions")
    public String create(ArticleForm form, HttpSession session) {
        User user = (User) session.getAttribute(SessionConst.LOGIN_USER);
        articleService.post(form, user.getUserId());
        return "redirect:/";
    }

    // 게시글 상세보기
    @GetMapping("/questions/{id}")
    public String findArticle(@PathVariable Long id, Model model){
        model.addAttribute("article", articleService.findOne(id));
        List<Reply> replies = replyService.findAll(id);
        model.addAttribute("reply", replies);
        model.addAttribute("replyNum", replies.size());
        return "qna/show";
    }

    // 게시글 수정
    @GetMapping("/questions/{id}/edit")
    public String editArticleForm(@PathVariable Long id, Model model, HttpSession session) {
        if (!articleService.isAuthCurrentUser(id, session)) {
            model.addAttribute("id", id);
            return "qna/edit_failed";
        }

        model.addAttribute("article", articleService.findOne(id));
        return "qna/edit";
    }

    @PutMapping("/questions/{id}/edit")
    public String updateArticle(@PathVariable Long id, ArticleForm form){
        articleService.update(id, form);
        return "redirect:/";
    }

    // 게시글 삭제
    @DeleteMapping("/questions/{id}/delete")
    public String deleteArticle(@PathVariable Long id, HttpSession session, Model model) {
        // 게시글 작성자와 현재 유저가 일치하지 않거나, 게시글과 댓글의 작성자가 일치하지 않으면
        if (!articleService.isAuthCurrentUser(id, session)
                || articleService.isWriterMatched(id, replyService.findAll(id))) {
            model.addAttribute("id", id);
            return "qna/edit_failed";
        }

        articleService.delete(id);
        return "redirect:/";
    }
    
}
