package kr.codesqaud.cafe.app.question.service;

import java.util.List;
import java.util.stream.Collectors;
import kr.codesqaud.cafe.app.comment.repository.CommentRepository;
import kr.codesqaud.cafe.app.question.controller.dto.QuestionResponse;
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
    private final CommentRepository commentRepository;

    public QuestionService(QuestionRepository repository, CommentRepository commentRepository) {
        this.repository = repository;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public QuestionResponse writeQuestion(QuestionSavedRequest questionRequest) {
        Question savedQuestion = repository.save(questionRequest.toEntity());
        return new QuestionResponse(savedQuestion);
    }

    public List<QuestionResponse> getAllQuestion() {
        return repository.findAll().stream()
            .map(QuestionResponse::new)
            .collect(Collectors.toUnmodifiableList());
    }

    public QuestionResponse findQuestion(Long id) {
        Question findQuestion = repository.findById(id).orElseThrow(() -> {
            throw new ResourceNotFoundException(QuestionErrorCode.NOT_FOUND_QUESTION);
        });
        return new QuestionResponse(findQuestion);
    }

    @Transactional
    public QuestionResponse modifyQuestion(Long id, QuestionSavedRequest questionRequest) {
        Question original = repository.findById(id).orElseThrow();
        original.modify(questionRequest.toEntity());
        return new QuestionResponse(repository.modify(original));
    }

    @Transactional
    public QuestionResponse delete(Long id) {
        commentRepository.deleteAllByQuestionId(id);
        return new QuestionResponse(repository.deleteById(id));
    }
}
