package kr.codesqaud.cafe.exception.article;

public class PrimaryKeyGenerationException extends RuntimeException {
    private static final String MESSAGE = "기본 키 생성에 실패했습니다.";

    public PrimaryKeyGenerationException() {
        super(MESSAGE);
    }
}
