package kr.codesqaud.cafe.question.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import kr.codesqaud.cafe.question.domain.Question;
import kr.codesqaud.cafe.question.dto.response.QuestionWriteDTO;

public class TempQuestionTable {
	private final List<Question> questions = new ArrayList<>();
	private final AtomicInteger questionIdx = new AtomicInteger(1);

	public synchronized void insert(QuestionWriteDTO dto) {
		questions.add(dto.toEntity(questionIdx.getAndIncrement()));
	}

	public List<Question> select() {
		return Collections.unmodifiableList(questions);
	}

	public int countAll() {
		return questions.size();
	}

}
