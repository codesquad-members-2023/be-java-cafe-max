package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.article.ArticleDTO;
import kr.codesqaud.cafe.service.ArticleService;
import kr.codesqaud.cafe.service.CommentService;
import kr.codesqaud.cafe.util.LoginSessionManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final CommentService commentService;
    private final LoginSessionManager loginSessionManager;

    public ArticleController(ArticleService articleService, CommentService commentService, LoginSessionManager loginSessionManager) {
        this.articleService = articleService;
        this.commentService = commentService;
        this.loginSessionManager = loginSessionManager;
    }


    @GetMapping("/posts/new")
    public String writeForm() {
        loginSessionManager.throwErrorIfAnonymous();
        return "post/form";
    }

    @PostMapping("/posts/new")
    public String writePost(@ModelAttribute final ArticleDTO articleDto) {
        articleService.write(articleDto);
        return "redirect:/";
    }

    @GetMapping("/posts/{id}")
    public String showPost(@PathVariable final long id, final Model model) {
        loginSessionManager.throwErrorIfAnonymous();
        model.addAttribute("loggedUser", loginSessionManager.getLoginUser().getId());
        model.addAttribute("wantedPost", articleService.findById(id));
        model.addAttribute("comments", commentService.gather(id));
        return "post/show";
    }

    @GetMapping("/posts/{id}/revision")
    public String modifyForm(@PathVariable final long id, final Model model) {
        if (isDifferentUser(id)) {
            return "error/403";
        }

        ArticleDTO wantedPost = articleService.findById(id);
        model.addAttribute("articleDTO", wantedPost);
        return "post/modifyForm";
    }

    //todo : @ModelAttribute 없어도 되는 원리 정리하기
    @PutMapping("/posts/{id}")
    public String modifyPost(@PathVariable final long id, final ArticleDTO articleDTO) {
        articleService.modify(id, articleDTO);
        return "redirect:/posts/{id}";
    }

    @DeleteMapping("/posts/{id}")
    public String deletePost(@PathVariable final long id) {
        if (isDifferentUser(id)) {
            return "error/403";
        }
        articleService.delete(id);
        return "redirect:/";
    }

    public boolean isDifferentUser(Long id) {
        return !articleService.isOwner(id);
    }
}

