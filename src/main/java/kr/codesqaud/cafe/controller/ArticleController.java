package kr.codesqaud.cafe.controller;

import javax.servlet.http.HttpSession;
import kr.codesqaud.cafe.SessionConstant;
import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.dto.ArticleDTO;
import kr.codesqaud.cafe.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/articles/new")
    public String articlePage() {
        return "qna/form";
    }

    @PostMapping("/articles")
    public String write(final ArticleDTO articleDTO, HttpSession session) {
        Article article = new Article(articleDTO.getTitle(), articleDTO.getContents());
        String writer = (String) session.getAttribute(SessionConstant.LOGIN_USER_ID);
        article.setWriter(writer); // TODO: 서비스 안으로 캡슐화

        articleService.save(article);
        logger.info("게시글 저장 성공");
        return "redirect:/";
    }

    @GetMapping("/articles/{index}")
    public String viewArticle(@PathVariable final long index, final Model model) {
        Article findArticle = articleService.findOne(index).get();
        model.addAttribute("article", findArticle);
        return "qna/show";
    }

    @GetMapping("/articles/{index}/edit")
    public String showEditPage(@PathVariable final long index, final Model model) {
        Article findArticle = articleService.findOne(index).get();
        System.out.println(findArticle.getSequence());
        model.addAttribute("article", findArticle);
        return "qna/edit_form";
    }

}
