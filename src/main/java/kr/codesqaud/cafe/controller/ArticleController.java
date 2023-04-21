package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.ArticleDTO;
import kr.codesqaud.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@Controller
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }


    @GetMapping("/posts/new")
    public String writeForm(HttpSession session) {
        if(isAnonymous(session)) return "redirect:/login";
        return "post/form";
    }

    @PostMapping("/posts/new-try")
    public String writePost(@ModelAttribute final ArticleDTO articleDto, HttpSession session) {
        articleService.write(articleDto, session);
        return "redirect:/";
    }

    @GetMapping("/posts/{id}")
    public String showPost(@PathVariable final long id, final Model model, HttpSession session) {
        if(isAnonymous(session)) return "redirect:/login";
        ArticleDTO wantedPost = articleService.findById(id);
        model.addAttribute("wantedPost", wantedPost);
        return "post/show";
    }

    @GetMapping("/posts/{id}/revision")
    public String modifyForm(@PathVariable final long id, final Model model) {
        ArticleDTO wantedPost = articleService.findById(id);
        model.addAttribute("articleDTO", wantedPost);
        return "post/modifyForm";
    }

    //todo : @ModelAttribute 없어도 되는 원리 정리하기
    @PutMapping("/posts/{id}/revision-try")
    public String modifyPost(@PathVariable final long id, final ArticleDTO articleDTO) {
        articleService.modify(id, articleDTO);
        return "redirect:/posts/{id}";
    }

    public boolean isAnonymous(HttpSession session) {
        return session.getAttribute("loginUser") == null;
    }
}

