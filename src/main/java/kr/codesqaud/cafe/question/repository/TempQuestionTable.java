package kr.codesqaud.cafe.question.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import kr.codesqaud.cafe.question.domain.Question;

public class TempQuestionTable {
	private final List<Question> questions = new ArrayList<>();
	private final AtomicInteger id = new AtomicInteger(1);

	public synchronized void insert(Question question) {
		Question questionGivenId = new Question(id.getAndIncrement(), question.getWriter(), question.getTitle(),
			question.getContents(), question.getRegistrationDateTime());
		questions.add(questionGivenId);
	}

	public List<Question> select() {
		return Collections.unmodifiableList(questions);
	}

	public int count() {
		return questions.size();
	}

}
