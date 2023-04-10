package kr.codesqaud.cafe.dummy;

import kr.codesqaud.cafe.dto.QuestionWriteDTO;
import kr.codesqaud.cafe.dto.SignUpDTO;
import kr.codesqaud.cafe.repository.temp_memory_db.TempQuestionTable;
import kr.codesqaud.cafe.repository.temp_memory_db.TempUserTable;

public class CollectionFrameworkRepositoryDummyData {

	/**
	 * 회원 목록에 dummy data를 넣어주는 메서드 입니다.
	 * @param table dummy data를 삽입할 회원 테이블
	 */
	public void insertUserDummyData(TempUserTable table) {
		String password = "qwe123!!!";
		table.insert(new SignUpDTO("admin", password, password, "관리자", "admin@mail.com"));
		table.insert(new SignUpDTO("apeintospace", password, password, "ape", "ape@mail.com"));
		table.insert(new SignUpDTO("user01", password, password, "유저", "user01@mail.com"));
		table.insert(new SignUpDTO("user02", password, password, "유저", "user02@mail.com"));
		table.insert(new SignUpDTO("user03", password, password, "유저", "user03@mail.com"));
		table.insert(new SignUpDTO("user04", password, password, "유저", "user04@mail.com"));
	}

	/**
	 * Q&A 게시판에 dummy data를 넣어주는 메서드 입니다.
	 * @param table dummy data를 삽입할 Q&A 게시판 테이블
	 */
	public void insertQuestionsDummyData(TempQuestionTable table) {
		for (int i = 1; i <= 500; i++) {
			table.insert(new QuestionWriteDTO("apeintospace", "title" + i, "content" + i));
		}
	}
}
