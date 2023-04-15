package kr.codesqaud.cafe.app.question.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import kr.codesqaud.cafe.app.question.controller.dto.QuestionResponse;
import kr.codesqaud.cafe.app.question.controller.dto.QuestionSavedRequest;
import kr.codesqaud.cafe.app.question.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class QuestionController {

    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    // 전체 게시글 조회
    @GetMapping({"/", "/qna"})
    public String home(Model model) {
        model.addAttribute("articles", questionService.getAllArticles());
        return "index";
    }


    // 특정 게시글 조회
    @GetMapping("/qna/{id}")
    public String detail(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("article", questionService.findArticle(id));
        return "qna/detail";
    }

    // 특정 게시글 추가
    @PostMapping("/qna")
    @ResponseBody
    public QuestionResponse create(@Valid @RequestBody QuestionSavedRequest requestDto) {
        logger.info("create : " + requestDto.toString());
        return questionService.write(requestDto);
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
