package kr.codesqaud.cafe.exception;

public class DeniedArticleModificationException extends RuntimeException {
	public DeniedArticleModificationException() {
		super("다른 사람의 글은 삭제 및 수정할 수 없습니다.");
	}
}
