package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.constant.SessionConst;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.ArticleForm;
import kr.codesqaud.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("qna/form")
    public String makeQna(HttpSession session, Model model) {
        User user = (User) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (user == null ) {
            return "redirect:/user/login";
        }
        model.addAttribute("loginUser", user);
        return "qna/form";
    }

    @PostMapping("qna/form")
    public String write(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                            User loginUser, ArticleForm form) {

        Article article = new Article(loginUser.getName(), form.getTitle(), form.getContents());
        articleService.write(article);

        return "redirect:/";

    }

    @GetMapping(value = {"/", "index"})
    public String list(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                           User loginUser, Model model) {

        model.addAttribute("loginUser", loginUser);

        List<Article> articles = articleService.findArticles();
        model.addAttribute("articles", articles);
        model.addAttribute("localDateTime", LocalDateTime.now());

        return "index";
    }

    @GetMapping("articles/{id}")
    public String showArticle(HttpSession session, Model model, @PathVariable Long id) {

        User user = (User) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (user == null ) {
            return "redirect:/user/login";
        }
        model.addAttribute("loginUser", user);
        model.addAttribute("article", articleService.findOne(id));
        return "qna/show";
    }
}
