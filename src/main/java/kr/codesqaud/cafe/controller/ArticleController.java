package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.constant.SessionConst;
import kr.codesqaud.cafe.dto.ArticleForm;
import kr.codesqaud.cafe.dto.ArticleUpdateForm;
import kr.codesqaud.cafe.dto.SessionDto;
import kr.codesqaud.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        SessionDto loginUser = (SessionDto) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (loginUser == null) {
            return "redirect:/user/login";
        }
        model.addAttribute("loginUser", loginUser);
        return "qna/form";
    }

    @PostMapping("qna/form")
    public String write(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                            SessionDto loginUser, ArticleForm form) {

        Article article = new Article(loginUser.getUserId(), loginUser.getName(), form.getTitle(), form.getContents());
        articleService.write(article);

        return "redirect:/";

    }

    @GetMapping(value = {"/", "index"})
    public String list(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                           SessionDto loginUser, Model model) {

        model.addAttribute("loginUser", loginUser);

        List<Article> articles = articleService.findArticles();
        model.addAttribute("articles", articles);
        model.addAttribute("localDateTime", LocalDateTime.now());

        return "index";
    }

    @GetMapping("articles/{id}")
    public String showArticle(HttpSession session, Model model, @PathVariable Long id) {

        SessionDto loginUser = (SessionDto) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if (loginUser == null) {
            return "redirect:/user/login";
        }
        model.addAttribute("loginUser", loginUser);
        model.addAttribute("article", articleService.findOne(id));
        return "qna/show";
    }

    @GetMapping("articles/update/{id}")
    public String updateForm(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                                 SessionDto loginUser, @PathVariable Long id, Model model) {

        model.addAttribute("loginUser", loginUser);

        return articleService.findOne(id)
                .filter(article -> articleService.isAuthorized(loginUser.getUserId(), id))
                .map(article -> {
                    model.addAttribute("article", article);
                    return "qna/update";
                })
                .orElse("error/404");
    }

    @PutMapping("articles/update/{id}")
    public String update(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                             SessionDto loginUser, ArticleUpdateForm form, @PathVariable Long id, Model model) {

        model.addAttribute("loginUser", loginUser);
        articleService.update(id, form.getTitle(), form.getContents());

        return "redirect:/articles/{id}";
    }

    @DeleteMapping("articles/delete/{id}")
    public String delete(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                             SessionDto loginUser, @PathVariable Long id, Model model) {

        model.addAttribute("loginUser", loginUser);


        if (articleService.isAuthorized(loginUser.getUserId(), id)) {
            articleService.delete(id);
            return "redirect:/";
        }

        return "error/404";
    }
}
