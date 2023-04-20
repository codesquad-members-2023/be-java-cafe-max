package kr.codesqaud.cafe.app.question.service;

import java.util.List;
import java.util.stream.Collectors;
import kr.codesqaud.cafe.app.question.entity.Question;
import kr.codesqaud.cafe.app.question.repository.QuestionRepository;
import kr.codesqaud.cafe.errors.errorcode.QuestionErrorCode;
import kr.codesqaud.cafe.errors.exception.RestApiException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuestionService {

    private final QuestionRepository repository;

    public QuestionService(QuestionRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Question write(Question question) {
        return repository.save(question);
    }

    public List<Question> findAllQuestions() {
        return repository.findAll().stream()
            .collect(Collectors.toUnmodifiableList());
    }

    public Question findQuestion(Long id) {
        return repository.findById(id).orElseThrow(() -> {
            throw new RestApiException(QuestionErrorCode.NOT_FOUND_ARTICLE);
        });
    }

    public Question modifyQuestion(Long id, Question requestQuestion) {
        Question original = findQuestion(id);
        Question modifiedQuestion =
            new Question(original.getId(),
                requestQuestion.getTitle(),
                requestQuestion.getContent(),
                original.getWriteDate(),
                original.getUserId());
        return repository.modify(modifiedQuestion);
    }
}
