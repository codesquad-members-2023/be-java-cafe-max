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
import kr.codesqaud.cafe.app.user.controller.dto.UserResponse;
import kr.codesqaud.cafe.app.user.entity.User;
import kr.codesqaud.cafe.app.user.service.UserService;
import kr.codesqaud.cafe.errors.errorcode.LoginErrorCode;
import kr.codesqaud.cafe.errors.errorcode.UserErrorCode;
import kr.codesqaud.cafe.errors.exception.RestApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class QuestionController {

    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);
    private final QuestionService questionService;
    private final UserService userService;

    public QuestionController(QuestionService questionService, UserService userService) {
        this.questionService = questionService;
        this.userService = userService;
    }

    // 전체 질문 목록 조회
    @GetMapping({"/", "/qna"})
    public ModelAndView listQuestion() {
        List<Question> questions = questionService.findAllQuestions();
        List<QuestionResponse> questionResponses = new ArrayList<>();
        for (Question question : questions) {
            User user = userService.findUser(question.getUserId());
            questionResponses.add(new QuestionResponse(question, user));
        }
        Collections.sort(questionResponses);
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("questions", questionResponses);
        return mav;
    }

    // 특정 질문 추가
    @PostMapping("/qna")
    public QuestionResponse addQuestion(@Valid @RequestBody QuestionSavedRequest requestDto) {
        User user = userService.findUser(requestDto.getUserId());
        Question question = requestDto.toEntity(user.getId());
        Question save = questionService.write(question);
        return new QuestionResponse(save, user);
    }

    // 특정 질문 조회
    @GetMapping("/qna/{id}")
    public ModelAndView detailQuestion(@PathVariable(value = "id") Long id, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return new ModelAndView(new RedirectView("/login"));
        }
        Question question = questionService.findQuestion(id);
        User user = userService.findUser(question.getUserId());
        QuestionResponse questionResponse = new QuestionResponse(question, user);
        ModelAndView mav = new ModelAndView("qna/detail");
        mav.addObject("question", questionResponse);
        return mav;
    }

    // 특정 질문 수정
    @PutMapping("/qna/{id}")
    public QuestionResponse editQuestion(
        @PathVariable(value = "id") Long id,
        @Valid @RequestBody QuestionSavedRequest requestDto,
        HttpSession session) {
        logger.info("{}, {}", id, requestDto.toString());
        User writer = userService.findUser(requestDto.getUserId());
        UserResponse loginUser = (UserResponse) session.getAttribute("user");
        // 비 로그인 상태인 경우
        if (loginUser == null) {
            throw new RestApiException(LoginErrorCode.UNAUTHORIZED);
        }
        // 다른 사용자의 질문 게시글을 수정하려는 경우
        if (!loginUser.getId().equals(writer.getId())) {
            throw new RestApiException(UserErrorCode.PERMISSION_DENIED);
        }

        Question requestQuestion = requestDto.toEntity(writer.getId());
        Question modifiedQuestion = questionService.modifyQuestion(id, requestQuestion);
        return new QuestionResponse(modifiedQuestion, writer);
    }

    // 질문 글쓰기 페이지
    @GetMapping("/qna/new")
    public ModelAndView addQuestionForm(HttpSession session) {
        ModelAndView mav = new ModelAndView("qna/new");
        if (session.getAttribute("user") == null) {
            mav.setView(new RedirectView("/login"));
        }
        return mav;
    }

    // 질문 수정 페이지
    @GetMapping("/qna/{id}/edit")
    public ModelAndView editQuestionForm(@PathVariable(value = "id") Long id, HttpSession session) {
        UserResponse loginUser = (UserResponse) session.getAttribute("user");
        Question original = questionService.findQuestion(id);
        User writer = userService.findUser(original.getUserId());

        // 비 로그인 상태인 경우
        if (loginUser == null) {
            throw new RestApiException(LoginErrorCode.UNAUTHORIZED);
        }

        // 다른 사용자의 질문 게시글을 수정하려는 경우
        if (!loginUser.getId().equals(writer.getId())) {
            throw new RestApiException(UserErrorCode.PERMISSION_DENIED);
        }

        ModelAndView mav = new ModelAndView("qna/edit");
        mav.addObject("question", new QuestionResponse(original, writer));
        return mav;
    }
}
