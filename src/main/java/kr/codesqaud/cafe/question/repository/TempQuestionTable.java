package kr.codesqaud.cafe.question.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import kr.codesqaud.cafe.question.domain.QuestionEntity;

public class TempQuestionTable {
	private final List<QuestionEntity> questions = new ArrayList<>();
	private final AtomicInteger id = new AtomicInteger(1);

	public synchronized void insert(QuestionEntity question) {
		QuestionEntity questionGivenId = new QuestionEntity(id.getAndIncrement(), question.getWriter_id(),
			question.getWriter(),
			question.getTitle(),
			question.getContents(),
			question.isIs_deleted(),
			question.getRegistrationDateTime());

		questions.add(questionGivenId);
	}

	public List<QuestionEntity> select() {
		return Collections.unmodifiableList(questions);
	}

	public int count() {
		return questions.size();
	}

	public synchronized boolean update(QuestionEntity question) {
		long id = question.getId();
		for (QuestionEntity exgistingQuestion : questions) {
			if (exgistingQuestion.getId() == id) {
				exgistingQuestion.updateFrom(question);
				return true;
			}
		}
		return false;
	}

	public synchronized boolean delete(long id) {
		for (QuestionEntity exgistingQuestion : questions) {
			if (exgistingQuestion.getId() == id) {
				exgistingQuestion.updateToDeleteState();
				return true;
			}
		}
		return false;
	}

}
