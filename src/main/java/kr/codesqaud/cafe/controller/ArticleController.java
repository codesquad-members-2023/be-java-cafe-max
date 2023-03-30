package kr.codesqaud.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArticleController {
    // TODO: WebMVCConfigurer 적용하기
    @GetMapping("/questions/form")
    public String postForm() {
        return "qna/form";
    }

    @GetMapping("/questions/show")
    public String showForm() {
        return "qna/show";
    }
}
