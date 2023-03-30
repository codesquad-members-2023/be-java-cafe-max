package kr.codesqaud.cafe.Controller;

import kr.codesqaud.cafe.Dto.ArticleFormDto;
import kr.codesqaud.cafe.Dto.UserFormDto;
import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.service.ArticleService;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/qna/create")
    public String createUser(ArticleFormDto articleFormDto) {
        articleService.save(new Article(articleFormDto.getAuthor(), articleFormDto.getTitle(), articleFormDto.getContents()));
        return "redirect:/";
    }

    @GetMapping("/")
    public String listArticles(Model model){
        model.addAttribute("articles", articleService.getArticleList());
        return "/index";
    }

    @GetMapping("/articles/{index}")
    public String showArticleDetail(Model model, @PathVariable Long index){
        model.addAttribute("articleDetail", articleService.getArticleDetail(index));
        return "/qna/show";
    }


}
