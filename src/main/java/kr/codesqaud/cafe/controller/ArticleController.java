package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.config.Session;
import kr.codesqaud.cafe.controller.dto.ArticleDTO;
import kr.codesqaud.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/article/submit/{loginUserId}")
    public String postArticle(@ModelAttribute @Valid ArticleDTO articleDto, HttpSession httpSession) {
        Session session = (Session) httpSession.getAttribute(Session.LOGIN_USER);
        articleDto.setId(session.getId());
        articleDto.setNickName(session.getNickName());
        articleService.post(articleDto);
        return "redirect:/";
    }

    @GetMapping("/article/{id}")
    public String showDetailArticle(@PathVariable int id, Model model) {
        model.addAttribute("article", articleService.findArticleById(id));
        return "post/show";
    }
}
