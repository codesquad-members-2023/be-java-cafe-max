package kr.codesqaud.cafe.repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.domain.Question;
import kr.codesqaud.cafe.dto.QuestionTitleDTO;
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

	public int countAll() {
		return questions.size();
	}

	public List<QuestionTitleDTO> selectQuestionTitlesByOffset(int postOffset, int pageSize) {
		return questions.stream()
			.sorted(Comparator.comparing(Question::getIdx).reversed())
			.skip(postOffset).limit(pageSize)
			.map(Question::toTitleDto)
			.collect(Collectors.toUnmodifiableList());
	}

}
