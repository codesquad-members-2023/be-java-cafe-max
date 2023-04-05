package kr.codesqaud.cafe.dummy;

import java.time.LocalDateTime;
import java.util.List;

import kr.codesqaud.cafe.domain.Question;
import kr.codesqaud.cafe.domain.User;

public class CollectionFrameworkRepositoryDummyData {

	public void insertUserDummyData(List<User> users) {
		String password = "qwe123!!!";
		users.add(new User(1, "admin", password, "관리자", "admin@mail.com"));
		users.add(new User(2, "apeintospace", password, "ape", "ape@mail.com"));
		users.add(new User(3, "user01", password, "유저01", "user01@mail.com"));
		users.add(new User(4, "user02", password, "유저02", "user02@mail.com"));
		users.add(new User(5, "user03", password, "유저03", "user03@mail.com"));
	}

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
