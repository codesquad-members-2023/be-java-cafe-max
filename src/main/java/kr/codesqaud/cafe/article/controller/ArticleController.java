package kr.codesqaud.cafe.article.controller;

import kr.codesqaud.cafe.article.dto.ArticleDetailDto;
import kr.codesqaud.cafe.article.dto.ArticleFormDto;
import kr.codesqaud.cafe.article.service.ArticleService;
import kr.codesqaud.cafe.utils.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;


    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }


    @GetMapping("/write-form")
    public String writeForm(HttpSession session) {
        return Session.isLoggedIn(session) ? "articles/write-form" : "redirect:/user/login";
    }

    //글 작성 클릭 시 매핑하고 글 저장
    @PostMapping("/create")
    public String createArticle(ArticleFormDto articleFormDto, HttpSession session) {
        articleService.save(articleFormDto, Session.getUserId(session));
        return "redirect:/articles/list";
    }

    //인덱스(홈)으로 매핑하여 글 목록을 보여줌
    @GetMapping("/list")
    public String listArticles(Model model) {
        model.addAttribute("articles", articleService.getPreviewDtos());
        return "articles/list";
    }

    //글 목록에서 제목 클릭시, 상세 글을 보여줌
    @GetMapping("/{id}")
    public String showArticleDetail(Model model, @PathVariable long id, HttpSession session) {
        model.addAttribute("articleDetail", articleService.getArticleDetail(id));
        return Session.isLoggedIn(session) ? "articles/show-detail" : "redirect:/user/login";
    }

    @GetMapping("/{id}/modify")
    public String showModifyForm(@PathVariable long id, Model model, HttpSession session) {
        ArticleDetailDto articleDetailDto = articleService.getArticleDetail(id);
        if (!Session.getUserId(session).equals(articleDetailDto.getAuthor())) {
            return "articles/forbidden";
        }
        model.addAttribute("articleDetail", articleService.getArticleDetail(id));
        return "articles/modify-form";
    }

    @PutMapping("/update")
    public String updateArticle(long id, ArticleFormDto articleFormDto) {
        articleService.update(id, articleFormDto);
        return "redirect:/articles/" + id;
    }

    @DeleteMapping("/delete")
    public String deleteArticle(long id, HttpSession session){
        ArticleDetailDto articleDetailDto = articleService.getArticleDetail(id);
        if (!Session.getUserId(session).equals(articleDetailDto.getAuthor())) {
            return "articles/forbidden";
        }

        articleService.delete(id);

        return "redirect:/articles/list";

    }


}
