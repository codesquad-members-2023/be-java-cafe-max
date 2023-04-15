package kr.codesqaud.cafe.app.question.service;

import java.util.List;
import java.util.stream.Collectors;
import kr.codesqaud.cafe.app.question.entity.Question;
import kr.codesqaud.cafe.app.question.repository.QuestionRepository;
import kr.codesqaud.cafe.app.user.entity.User;
import kr.codesqaud.cafe.app.question.controller.dto.QuestionResponse;
import kr.codesqaud.cafe.app.question.controller.dto.QuestionSavedRequest;
import kr.codesqaud.cafe.errors.errorcode.QuestionErrorCode;
import kr.codesqaud.cafe.errors.exception.RestApiException;
import kr.codesqaud.cafe.app.user.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

    private final QuestionRepository repository;
    private final UserService userService;

    public QuestionService(QuestionRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    public QuestionResponse write(QuestionSavedRequest requestDto) {
        User user = userService.findUser(requestDto.getUserId());
        Question save = repository.save(requestDto.toEntity(user));
        return new QuestionResponse(save);
    }

    public List<QuestionResponse> getAllArticles() {
        return repository.findAll().stream()
            .map(QuestionResponse::new)
            .sorted()
            .collect(Collectors.toUnmodifiableList());
    }

    public QuestionResponse findArticle(Long id) {
        Question question = validateExistArticle(id);
        return new QuestionResponse(question);
    }

    private Question validateExistArticle(Long id) {
        return repository.findById(id).orElseThrow(() -> {
            throw new RestApiException(QuestionErrorCode.NOT_FOUND_ARTICLE);
        });
    }
}
