package kr.codesqaud.cafe.article.controller;

import kr.codesqaud.cafe.article.domain.Article;
import kr.codesqaud.cafe.article.dto.ArticleFormDto;
import kr.codesqaud.cafe.article.service.ArticleService;
import kr.codesqaud.cafe.utils.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }


    @GetMapping("/write-form")
    public String writeForm(HttpSession session){
        return Session.isLoggedIn(session) ? "articles/write-form" : "redirect:/user/login" ;
    }

    //글 작성 클릭 시 매핑하고 글 저장
    @PostMapping("/create")
    public String createUser(ArticleFormDto articleFormDto) {
        articleService.save(new Article(articleFormDto.getAuthor(), articleFormDto.getTitle(), articleFormDto.getContents()));
        return "redirect:/articles/list";
    }

    //인덱스(홈)으로 매핑하여 글 목록을 보여줌
    @GetMapping("/list")
    public String listArticles(Model model) {
        model.addAttribute("articles", articleService.getArticleListDtos());
        return "articles/list";
    }

    //글 목록에서 제목 클릭시, 상세 글을 보여줌
    @GetMapping("/{index}")
    public String showArticleDetail(Model model, @PathVariable Long index, HttpSession session) {
        model.addAttribute("articleDetail", articleService.getArticleDetail(index));
        return Session.isLoggedIn(session) ? "articles/show-detail" : "redirect:/user/login" ;
    }


}
