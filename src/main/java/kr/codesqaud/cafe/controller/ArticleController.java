package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.article.Article;
import kr.codesqaud.cafe.dto.ArticleFormDto;
import kr.codesqaud.cafe.dto.LoginSessionDto;
import kr.codesqaud.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;


@Controller
public class ArticleController {
    private final ArticleService articleService;
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/article/question")
    public String getWriteForm(HttpSession session){
        articleService.checkLogin(session);
        return "qna/form";
    }

    @PostMapping("/article/write")
    public String postQuestion(ArticleFormDto articleFormDto,HttpSession session) {
        articleService.writeArticle(articleFormDto,session);
        return "redirect:/";
    }

    @GetMapping("/article/show/{index}")
    public String getShow(@PathVariable int index, Model model, HttpSession session) {
        articleService.checkLogin(session);
        Article article = articleService.findByIdx(index);
        model.addAttribute("article", article);
        model.addAttribute("auth",articleService.checkAuth(article.getUserId(),session));
        return "qna/show";
    }


    @GetMapping("/")
    public String getIndex(Model model) {
        model.addAttribute("articleList", articleService.getAricleList());
        return "index";
    }

}
