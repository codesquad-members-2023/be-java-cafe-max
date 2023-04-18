package kr.codesqaud.cafe.common.repository;

import kr.codesqaud.cafe.question.domain.Question;
import kr.codesqaud.cafe.question.repository.TempQuestionTable;
import kr.codesqaud.cafe.user.domain.User;
import kr.codesqaud.cafe.user.repository.TempUserTable;

public class CollectionFrameworkRepositoryDummyData {

	/**
	 * 회원 목록에 dummy data를 넣어주는 메서드 입니다.
	 * @param table dummy data를 삽입할 회원 테이블
	 */
	public void insertUserDummyData(TempUserTable table) {
		String password = "qwe123!!!";
		table.insert(new User("admin", password, "관리자", "admin@mail.com"));
		table.insert(new User("apeintospace", password, "ape", "ape@mail.com"));
		table.insert(new User("user01", password, "유저", "user01@mail.com"));
		table.insert(new User("user02", password, "유저", "user02@mail.com"));
		table.insert(new User("user03", password, "유저", "user03@mail.com"));
		table.insert(new User("user04", password, "유저", "user04@mail.com"));
	}

	/**
	 * Q&A 게시판에 dummy data를 넣어주는 메서드 입니다.
	 * @param table dummy data를 삽입할 Q&A 게시판 테이블
	 */
	public void insertQuestionsDummyData(TempQuestionTable table) {
		for (int i = 1; i <= 500; i++) {
			table.insert(new Question("apeintospace", "title" + i, "content" + i));
		}
	}
}
