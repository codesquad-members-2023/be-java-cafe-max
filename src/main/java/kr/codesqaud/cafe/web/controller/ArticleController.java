package kr.codesqaud.cafe.web.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import kr.codesqaud.cafe.web.dto.article.ArticleResponseDto;
import kr.codesqaud.cafe.web.dto.article.ArticleSavedRequestDto;
import kr.codesqaud.cafe.web.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ArticleController {

    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);
    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    // 특정 게시글 조회
    @GetMapping("/qna/{id}")
    public String detail(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("article", articleService.findArticle(id));
        return "qna/detail";
    }

    // 특정 게시글 추가
    @PostMapping("/qna")
    @ResponseBody
    public ArticleResponseDto create(@Valid @RequestBody ArticleSavedRequestDto requestDto) {
        logger.info("create : " + requestDto.toString());
        return articleService.write(requestDto);
    }

    // 게시글 글쓰기 페이지
    @GetMapping("/qna/form")
    public ModelAndView form(HttpSession session) {
        ModelAndView mav = new ModelAndView("qna/form");
        if (session.getAttribute("user") == null) {
            mav.setView(new RedirectView("/user/login"));
        }
        return mav;
    }
}
