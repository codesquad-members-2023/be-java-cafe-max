package kr.codesqaud.cafe.repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.domain.Question;
import kr.codesqaud.cafe.dto.QuestionDetailsDTO;
import kr.codesqaud.cafe.dto.QuestionTitleDTO;
import kr.codesqaud.cafe.dto.QuestionWriteDTO;
import kr.codesqaud.cafe.dummy.CollectionFrameworkRepositoryDummyData;

@Repository
public class QuestionRepository {
	private final List<Question> questions;
	private int questionIdx = 1;

	public QuestionRepository() {
		this.questions = new ArrayList<>();
		CollectionFrameworkRepositoryDummyData dummyData = new CollectionFrameworkRepositoryDummyData();
		dummyData.insertQuestionsDummyData(this.questions);
	}

	public synchronized void insert(QuestionWriteDTO dto) {
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

	public QuestionDetailsDTO selectByIdx(int idx) throws NoSuchElementException {
		for (Question question : questions) {
			if (question.getIdx() == idx) {
				return question.toDetailsDto();
			}
		}
		throw new NoSuchElementException("존재하지 않는 개시글 입니다.");
	}

}
