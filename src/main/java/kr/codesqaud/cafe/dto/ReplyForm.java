package kr.codesqaud.cafe.dto;

public class ReplyForm {

    private final Long articleId;
    private final String writer;
    private final String contents;

    public ReplyForm(Long articleId, String writer, String contents) {
        this.articleId = articleId;
        this.writer = writer;
        this.contents = contents;
    }

    public Long getArticleId() {
        return articleId;
    }

    public String getWriter() {
        return writer;
    }

    public String getContents() {
        return contents;
    }
}
