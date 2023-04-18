package kr.codesqaud.cafe.question.repository;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.common.repository.CollectionFrameworkRepositoryDummyData;
import kr.codesqaud.cafe.question.domain.Question;

@Repository
public class CollectionFrameWorkQuestionRepository implements QuestionRepository {
	private final TempQuestionTable questionTable;

	public CollectionFrameWorkQuestionRepository() {
		questionTable = new TempQuestionTable();
		CollectionFrameworkRepositoryDummyData dummyData = new CollectionFrameworkRepositoryDummyData();
		dummyData.insertQuestionsDummyData(questionTable);
	}

	public void save(Question question) {
		questionTable.insert(question);
	}

	public long countBy() {
		return questionTable.count();
	}

	public List<Question> findAll(long offset, int pageSize) {
		return questionTable.select().stream()
			.sorted(Comparator.comparing(Question::getId).reversed())
			.skip(offset).limit(pageSize)
			.collect(Collectors.toUnmodifiableList());
	}

	public Question findById(long id) throws NoSuchElementException {
		for (Question question : questionTable.select()) {
			if (question.getId() == id) {
				return question;
			}
		}
		throw new NoSuchElementException("존재하지 않는 개시글 입니다.");
	}

}
