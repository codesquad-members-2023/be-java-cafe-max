package kr.codesqaud.cafe.question.repository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.common.repository.CollectionFrameworkRepositoryDummyData;
import kr.codesqaud.cafe.question.domain.QuestionEntity;
import kr.codesqaud.cafe.question.exception.QuestionNotExistException;

@Repository
public class CollectionFrameWorkQuestionRepository implements QuestionRepository {
	private final TempQuestionTable questionTable;

	public CollectionFrameWorkQuestionRepository() {
		questionTable = new TempQuestionTable();
		CollectionFrameworkRepositoryDummyData dummyData = new CollectionFrameworkRepositoryDummyData();
		dummyData.insertQuestionsDummyData(questionTable);
	}

	public void save(QuestionEntity question) {
		questionTable.insert(question);
	}

	public long countBy() {
		return questionTable.count();
	}

	public List<QuestionEntity> findPageBy(long offset, int pageSize) {
		return questionTable.select().stream()
			.sorted(Comparator.comparing(QuestionEntity::getId).reversed())
			.skip(offset).limit(pageSize)
			.collect(Collectors.toUnmodifiableList());
	}

	public QuestionEntity findById(long id) throws QuestionNotExistException {
		for (QuestionEntity question : questionTable.select()) {
			if (question.getId() == id) {
				return question;
			}
		}
		throw new QuestionNotExistException(id);
	}

}
