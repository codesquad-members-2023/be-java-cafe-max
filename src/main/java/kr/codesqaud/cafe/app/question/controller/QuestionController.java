package kr.codesqaud.cafe.app.question.controller;

import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import kr.codesqaud.cafe.app.comment.service.CommentService;
import kr.codesqaud.cafe.app.question.controller.dto.QuestionResponse;
import kr.codesqaud.cafe.app.question.controller.dto.QuestionSavedRequest;
import kr.codesqaud.cafe.app.question.service.QuestionService;
import kr.codesqaud.cafe.app.user.controller.dto.UserResponse;
import kr.codesqaud.cafe.errors.errorcode.UserErrorCode;
import kr.codesqaud.cafe.errors.exception.RestApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class QuestionController {

    private static final Logger log = LoggerFactory.getLogger(QuestionController.class);
    private final QuestionService questionService;
    private final CommentService commentService;

    public QuestionController(QuestionService questionService, CommentService commentService) {
        this.questionService = questionService;
        this.commentService = commentService;
    }

    // 전체 질문 목록 조회
    @GetMapping({"/", "/qna"})
    public ModelAndView listQuestion() {
        List<QuestionResponse> questions = questionService.getAllQuestion();
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("questions", questions);
        return mav;
    }

    // 특정 질문 추가
    @PostMapping("/qna")
    public QuestionResponse addQuestion(@Valid @RequestBody QuestionSavedRequest questionRequest) {
        return questionService.writeQuestion(questionRequest);
    }

    // 특정 질문 조회
    @GetMapping("/qna/{id}")
    public ModelAndView detailQuestion(@PathVariable(value = "id") Long id) {
        ModelAndView mav = new ModelAndView("qna/detail");
        mav.addObject("question", questionService.findQuestion(id));
        mav.addObject("comments", commentService.getComments(id));
        return mav;
    }

    // TODO : 인증 인터셉터 추가
    // 특정 질문 수정
    @PutMapping("/qna/{id}")
    public QuestionResponse editQuestion(
        @PathVariable(value = "id") Long id,
        @Valid @RequestBody QuestionSavedRequest questionRequest,
        HttpSession session) {
        UserResponse user = (UserResponse) session.getAttribute("user");
        QuestionResponse question = questionService.findQuestion(id);
        if (!question.getUserId().equals(user.getId())) {
            throw new RestApiException(UserErrorCode.PERMISSION_DENIED);
        }

        return questionService.modifyQuestion(id, questionRequest);
    }

    // TODO : 인증 인터셉터 추가
    // 특정 질문 삭제
    @DeleteMapping("/qna/{id}")
    public QuestionResponse deleteQuestion(@PathVariable(value = "id") Long id,
        HttpSession session) {
        log.info(id.toString());
        UserResponse loginUser = (UserResponse) session.getAttribute("user");
        QuestionResponse question = questionService.findQuestion(id);
        if (!question.getUserId().equals(loginUser.getId())) {
            throw new RestApiException(UserErrorCode.PERMISSION_DENIED);
        }

        return questionService.delete(id);
    }

    // 질문 글쓰기 페이지
    @GetMapping("/qna/new")
    public ModelAndView addQuestionForm() {
        return new ModelAndView("qna/new");
    }

    // 질문 수정 페이지
    @GetMapping("/qna/{id}/edit")
    public ModelAndView editQuestionForm(@PathVariable(value = "id") Long id) {
        ModelAndView mav = new ModelAndView("qna/edit");
        mav.addObject("question", questionService.findQuestion(id));
        return mav;
    }
}
