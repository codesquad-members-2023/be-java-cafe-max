package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.dto.ArticleDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    @PostMapping("/qna/write")
    public String writeArticle(ArticleDto articleDto) {
        return "redirect:/";
    }
}
