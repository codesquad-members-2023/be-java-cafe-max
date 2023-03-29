package kr.codesqaud.cafe.Controller;

import kr.codesqaud.cafe.Dto.ArticleFormDto;
import kr.codesqaud.cafe.Dto.UserFormDto;
import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.service.ArticleService;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/user/create")
    public String createUser(ArticleFormDto articleFormDto) {
        articleService.write(new Article(articleFormDto.getWriter(), articleFormDto.getTitle(), articleFormDto.getContents()));
        return "redirect:/";
    }

    
}
