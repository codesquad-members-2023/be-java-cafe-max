package kr.codesqaud.cafe.question.repository;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.common.repository.CollectionFrameworkRepositoryDummyData;
import kr.codesqaud.cafe.question.domain.Question;
import kr.codesqaud.cafe.question.dto.request.QuestionWriteRequestDTO;
import kr.codesqaud.cafe.question.dto.response.QuestionDetailResponseDTO;
import kr.codesqaud.cafe.question.dto.response.QuestionTitleResponseDTO;

@Repository
public class CollectionFrameWorkQuestionRepository implements QuestionRepository {
	private final TempQuestionTable questionTable;

	public CollectionFrameWorkQuestionRepository() {
		questionTable = new TempQuestionTable();
		CollectionFrameworkRepositoryDummyData dummyData = new CollectionFrameworkRepositoryDummyData();
		dummyData.insertQuestionsDummyData(questionTable);
	}

	public void insert(QuestionWriteRequestDTO dto) {
		questionTable.insert(dto);
	}

	public int countAll() {
		return questionTable.countAll();
	}

	public List<QuestionTitleResponseDTO> selectQuestionTitlesByOffset(int postOffset, int pageSize) {
		return questionTable.select().stream()
			.sorted(Comparator.comparing(Question::getId).reversed())
			.skip(postOffset).limit(pageSize)
			.map(Question::toTitleDto)
			.collect(Collectors.toUnmodifiableList());
	}

	public QuestionDetailResponseDTO selectById(int id) throws NoSuchElementException {
		for (Question question : questionTable.select()) {
			if (question.getId() == id) {
				return question.toDetailsDto();
			}
		}
		throw new NoSuchElementException("존재하지 않는 개시글 입니다.");
	}

}
