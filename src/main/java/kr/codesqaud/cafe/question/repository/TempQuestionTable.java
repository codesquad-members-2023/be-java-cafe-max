package kr.codesqaud.cafe.question.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import kr.codesqaud.cafe.question.domain.Question;
import kr.codesqaud.cafe.question.dto.request.QuestionWriteRequestDTO;

public class TempQuestionTable {
	private final List<Question> questions = new ArrayList<>();
	private final AtomicInteger id = new AtomicInteger(1);

	public synchronized void insert(QuestionWriteRequestDTO dto) {
		questions.add(dto.toEntity(id.getAndIncrement()));
	}

	public List<Question> select() {
		return Collections.unmodifiableList(questions);
	}

	public int countAll() {
		return questions.size();
	}

}
