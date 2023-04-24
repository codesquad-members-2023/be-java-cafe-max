package kr.codesqaud.cafe.comment.dto;

public class RequestCommentForm {

    private final long articleId;

    private final String contents;

    public RequestCommentForm(long articleId, String contents) {
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
