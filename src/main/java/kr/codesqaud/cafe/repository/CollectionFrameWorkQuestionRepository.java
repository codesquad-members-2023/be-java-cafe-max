package kr.codesqaud.cafe.repository;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.domain.Question;
import kr.codesqaud.cafe.dto.QuestionDetailDTO;
import kr.codesqaud.cafe.dto.QuestionTitleDTO;
import kr.codesqaud.cafe.dto.QuestionWriteDTO;
import kr.codesqaud.cafe.dummy.CollectionFrameworkRepositoryDummyData;
import kr.codesqaud.cafe.repository.temp_memory_db.TempQuestionTable;

@Repository
public class CollectionFrameWorkQuestionRepository {
	private final TempQuestionTable questionTable;

	public CollectionFrameWorkQuestionRepository() {
		questionTable = new TempQuestionTable();
		CollectionFrameworkRepositoryDummyData dummyData = new CollectionFrameworkRepositoryDummyData();
		dummyData.insertQuestionsDummyData(questionTable);
	}

	public synchronized void insert(QuestionWriteDTO dto) {
		questionTable.insert(dto);
	}

	public int countAll() {
		return questionTable.countAll();
	}

	public List<QuestionTitleDTO> selectQuestionTitlesByOffset(int postOffset, int pageSize) {
		return questionTable.select().stream()
			.sorted(Comparator.comparing(Question::getIdx).reversed())
			.skip(postOffset).limit(pageSize)
			.map(Question::toTitleDto)
			.collect(Collectors.toUnmodifiableList());
	}

	public QuestionDetailDTO selectByIdx(int idx) throws NoSuchElementException {
		for (Question question : questionTable.select()) {
			if (question.getIdx() == idx) {
				return question.toDetailsDto();
			}
		}
		throw new NoSuchElementException("존재하지 않는 개시글 입니다.");
	}

}
