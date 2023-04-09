package kr.codesqaud.cafe.exception.article;

public class ArticleNotFoundException extends RuntimeException {
    private static final String MESSAGE = "존재하지 않는 게시글입니다.";

    public ArticleNotFoundException() {
        super(MESSAGE);
    }
}
