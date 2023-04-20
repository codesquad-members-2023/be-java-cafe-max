package kr.codesqaud.cafe.app.question.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import kr.codesqaud.cafe.app.question.entity.Question;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryQuestionRepository implements QuestionRepository {

    private final List<Question> store = new ArrayList<>();
    private static long sequence = 0;

    @Override
    public List<Question> findAll() {
        return Collections.unmodifiableList(store);
    }

    @Override
    public Optional<Question> findById(Long id) {
        return store.stream().filter(article -> article.getId().equals(id)).findFirst();
    }

    @Override
    public Question save(Question question) {
        Question newQuestion =
            new Question(nextId(),
                question.getTitle(),
                question.getContent(),
                question.getWriteDate(),
                question.getUserId());
        store.add(newQuestion);
        return newQuestion;
    }

    @Override
    public Question modify(Question question) {
        store.remove(question);
        store.add(question);
        return question;
    }

    @Override
    public int deleteById(Long id) {
        findById(id).ifPresent(store::remove);
        return 1;
    }

    private synchronized Long nextId() {
        return ++sequence;
    }
}
