package kr.codesqaud.cafe.exception;

public class ArticleNotFoundException extends RuntimeException {

    public ArticleNotFoundException() {
        super("존재하지 않는 글입니다.");
    }
}
