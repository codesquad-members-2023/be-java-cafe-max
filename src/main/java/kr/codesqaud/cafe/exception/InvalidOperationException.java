package kr.codesqaud.cafe.exception;

public class InvalidOperationException extends BusinessException {

	public InvalidOperationException(final Long articleId) {
		super(String.format("%d번 글을 삭제할 수 없습니다.", articleId));
	}
}
