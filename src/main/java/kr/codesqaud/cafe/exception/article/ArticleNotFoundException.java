package kr.codesqaud.cafe.exception.article;

public class ArticleNotFoundException extends RuntimeException {
    public ArticleNotFoundException() {
        super("존재하지 않는 게시글입니다.");
    }
}
