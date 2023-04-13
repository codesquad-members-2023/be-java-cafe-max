package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.article.Article;
import kr.codesqaud.cafe.dto.ArticleFormDto;
import kr.codesqaud.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


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
    public String postQuestion(@Valid @ModelAttribute ArticleFormDto articleFormDto, HttpSession session) {
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

    @GetMapping("/article/update/{index}")
    public String getUpdatePage(@PathVariable int index,Model model ,HttpSession session){
        Article article = articleService.findByIdx(index);
        articleService.checkLoginAuth(article.getUserId(),session);
        model.addAttribute("setTitle",article.getTitle());
        model.addAttribute("setContent",article.getContents());
        model.addAttribute("index",index);
        return "qna/update_form";
    }

    @PutMapping("/article/update/{index}")
    public String putUpdate(@PathVariable int index, @Valid @ModelAttribute ArticleFormDto dto, HttpSession session){
        articleService.update(index, dto,session);
        return "redirect:/article/show/"+index;
    }

    @DeleteMapping("/article/delete/{index}")
    public String delete(@PathVariable int index){
        articleService.delete(index);
        return "redirect:/";
    }

}
