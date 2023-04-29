package kr.codesqaud.cafe.app.question.repository;

import java.util.List;
import java.util.Optional;
import kr.codesqaud.cafe.app.question.entity.Question;

public interface QuestionRepository {

    List<Question> findAll();

    Optional<Question> findById(Long id);

    Question save(Question question);

    Question modify(Question question);

    Question deleteById(Long id);

}
