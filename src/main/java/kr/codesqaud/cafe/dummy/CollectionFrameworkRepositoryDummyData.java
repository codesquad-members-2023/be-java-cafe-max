package kr.codesqaud.cafe.dummy;

import java.time.LocalDateTime;
import java.util.List;

import kr.codesqaud.cafe.domain.Question;

public class CollectionFrameworkRepositoryDummyData {

	/**
	 * Q&A 게시판에 dummy data를 넣어주는 메서드 입니다.
	 * @param questions Q&A 게시판 dummy data를 삽입할 List형 임시 저장소
	 */
	public void insertQuestionsDummyData(List<Question> questions) {
		for (int i = 1; i <= 500; i++) {
			questions.add(new Question(i, "apeintospace", "title" + i, "content" + i, LocalDateTime.now()));
		}
	}
}
