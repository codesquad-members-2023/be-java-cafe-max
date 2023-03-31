package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.ArticleMemoryRepository;
import kr.codesqaud.cafe.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ArticleController {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleController(ArticleMemoryRepository articleMemoryRepository) {
        this.articleRepository = articleMemoryRepository;
    }

    @GetMapping("/qna/write")
    public String write(){
        return "qna/form";
    }

    @PostMapping("/qna/create")
    public String create(
            @RequestParam("writer") String writer,
            @RequestParam("title") String title,
            @RequestParam("contents") String contents,
            Model model
    ){
        Article article = new Article();

        article.setWriter(writer);
        article.setTitle(title);
        article.setContents(contents);

        articleRepository.save(article);

        model.addAttribute("article", article);

        return "redirect:/";
    }

    @GetMapping("/")
    public String showIndex(Model model){
        model.addAttribute("articles", articleRepository.findAll());

        return "index";
    }

}
