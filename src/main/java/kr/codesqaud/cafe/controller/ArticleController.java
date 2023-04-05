package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.ArticleForm;
import kr.codesqaud.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class ArticleController {
    ArticleService articleService = new ArticleService();

    @GetMapping("/write")
    public String showWrite (ArticleForm articleForm){
        return "qna/form";
    }

}
