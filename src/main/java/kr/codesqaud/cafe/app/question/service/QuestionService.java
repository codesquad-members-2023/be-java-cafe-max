package kr.codesqaud.cafe.app.question.service;

import java.util.List;
import java.util.stream.Collectors;
import kr.codesqaud.cafe.app.question.controller.dto.QuestionSavedRequest;
import kr.codesqaud.cafe.app.question.entity.Question;
import kr.codesqaud.cafe.app.question.repository.QuestionRepository;
import kr.codesqaud.cafe.errors.errorcode.QuestionErrorCode;
import kr.codesqaud.cafe.errors.exception.ResourceNotFoundException;
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
            throw new ResourceNotFoundException(QuestionErrorCode.NOT_FOUND_QUESTION);
        });
    }

    // TODO: 로직 수정
    @Transactional
    public Question modifyQuestion(Long id, QuestionSavedRequest requestQuestion) {
        Question original = findQuestion(id);
        Question modifiedQuestion =
            new Question(original.getId(),
                requestQuestion.getTitle(),
                requestQuestion.getContent(),
                original.getCreateTime(),
                original.getModifyTime(),
                original.getUserId());
        return repository.modify(modifiedQuestion);
    }

    @Transactional
    public Question delete(Long id) {
        return repository.deleteById(id);
    }
}
