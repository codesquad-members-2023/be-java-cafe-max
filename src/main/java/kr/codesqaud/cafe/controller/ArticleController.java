package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.article.Article;
import kr.codesqaud.cafe.dto.ArticleFormDto;
import kr.codesqaud.cafe.service.memory.ArticleMemoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ArticleController {

    private final ArticleMemoryService articleMemoryService;

    @Autowired
    public ArticleController(ArticleMemoryService articleMemoryService) {
        this.articleMemoryService = articleMemoryService;
    }

    @PostMapping("/article/write")
    public String postQuestion(ArticleFormDto articleFormDto) {
        articleMemoryService.questionWrite(articleFormDto);
        return "redirect:/";
    }

    @GetMapping("/article/show/{index}")
    public String getShow(@PathVariable int index, Model model) {
        List<Article> list = articleMemoryService.getArticleList();
        model.addAttribute("article", list.get(index));
        return "qna/show";
    }


    @GetMapping("/")
    public String getIndex(Model model) {
        model.addAttribute("articleList", articleMemoryService.getArticleList());
        return "index";
    }

}
