package kr.codesqaud.cafe.exception;

public class ArticleNotFoundException extends RuntimeException{
    public ArticleNotFoundException() {
        super("해당 글이 존재하지 않습니다.");
    }
}
