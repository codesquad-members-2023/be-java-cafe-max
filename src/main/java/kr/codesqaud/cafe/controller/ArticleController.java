package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.dto.ArticleDto;
import kr.codesqaud.cafe.dto.ArticleFormDto;
import kr.codesqaud.cafe.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    private final ArticleService articleService;
    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/article/question")
    public String getQuestionPage(){
        return "/qna/form";
    }
    @PostMapping("/article/question")
    public String postQuestion(ArticleFormDto articleFormDto){
        String writer = articleFormDto.getWriter();
        String title = articleFormDto.getTitle();
        String contents = articleFormDto.getContents();
        ArticleDto articleDto = new ArticleDto(writer,title,contents);
        articleService.questionWrite(articleDto);
        return "redirect:/";
    }

    @GetMapping("/")
    public String getIndex(Model model){
        model.addAttribute("articleList",articleService.getArticleList());
        return "index";
    }

}
