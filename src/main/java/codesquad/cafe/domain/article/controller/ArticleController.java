package codesquad.cafe.domain.article.controller;

import codesquad.cafe.domain.article.dto.ArticleRequestDto;
import codesquad.cafe.domain.article.dto.ArticleResponseDto;
import codesquad.cafe.domain.article.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(final ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public String showHome(Model model) {
        List<ArticleResponseDto> posts = articleService.findPosts();
        model.addAttribute("posts", posts);
        return "index";
    }

    @PostMapping("/questions")
    public String writePost(@ModelAttribute @Valid ArticleRequestDto articleRequestDto) {
        articleService.createPost(articleRequestDto);
        return "redirect:/";
    }

    @GetMapping("/articles/{postId}")
    public String showDetailPost(@PathVariable Long postId, Model model) {
        ArticleResponseDto post = articleService.findPost(postId);
        model.addAttribute("post", post);
        return "qna/show";
    }
}
