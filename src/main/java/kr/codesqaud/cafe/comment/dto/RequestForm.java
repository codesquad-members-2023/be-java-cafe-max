package kr.codesqaud.cafe.comment.dto;

public class RequestForm {

    private final long articleId;

    private final String contents;

    public RequestForm(long articleId, String contents) {
        this.articleId = articleId;
        this.contents = contents;
    }

    public long getArticleId() {
        return articleId;
    }

    public String getContents() {
        return contents;
    }

}
