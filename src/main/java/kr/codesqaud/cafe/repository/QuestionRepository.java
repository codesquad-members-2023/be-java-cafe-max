package kr.codesqaud.cafe.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.domain.Question;
import kr.codesqaud.cafe.dto.QuestionWriteDTO;

@Repository
public class QuestionRepository {
	private List<Question> questions;
	private int questionIdx = 1;

	public QuestionRepository() {
		this.questions = new ArrayList<>();
	}

	public synchronized void save(QuestionWriteDTO dto) {
		questions.add(dto.toEntity(questionIdx++));
	}

}
