package kr.codesqaud.cafe.app.question.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import kr.codesqaud.cafe.app.question.controller.dto.QuestionResponse;
import kr.codesqaud.cafe.app.question.controller.dto.QuestionSavedRequest;
import kr.codesqaud.cafe.app.question.entity.Question;
import kr.codesqaud.cafe.app.question.service.QuestionService;
import kr.codesqaud.cafe.app.user.entity.User;
import kr.codesqaud.cafe.app.user.service.UserService;
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
    private final UserService userService;

    public QuestionController(QuestionService questionService, UserService userService) {
        this.questionService = questionService;
        this.userService = userService;
    }

    // 전체 게시글 조회
    @GetMapping({"/", "/qna"})
    public String home(Model model) {
        List<Question> questions = questionService.findAllQuestions();
        List<QuestionResponse> questionResponses = new ArrayList<>();
        for (Question question : questions) {
            User user = userService.findUser(question.getUserId());
            questionResponses.add(new QuestionResponse(question, user));
        }
        Collections.sort(questionResponses);
        model.addAttribute("questions", questionResponses);
        return "index";
    }

    // 특정 게시글 조회
    @GetMapping("/qna/{id}")
    public String detail(@PathVariable(value = "id") Long id, Model model) {
        Question question = questionService.findQuestion(id);
        User user = userService.findUser(question.getUserId());
        QuestionResponse questionResponse = new QuestionResponse(question, user);
        model.addAttribute("question", questionResponse);
        return "qna/detail";
    }

    // 특정 게시글 추가
    @PostMapping("/qna")
    @ResponseBody
    public QuestionResponse create(@Valid @RequestBody QuestionSavedRequest requestDto) {
        logger.info("create : " + requestDto.toString());
        User user = userService.findUser(requestDto.getUserId());
        Question question = requestDto.toEntity(user.getId());
        Question save = questionService.write(question);
        return new QuestionResponse(save, user);
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
