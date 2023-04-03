package kr.codesqaud.cafe.dto.article;

public class ArticleSaveRequest {
    private String title;
    private String writer;
    private String contents;

    public ArticleSaveRequest(String title, String writer, String contents) {
        this.title = title;
        this.writer = writer;
        this.contents = contents;
    }

    public String getTitle() { return title; }
    public String getWriter() { return writer; }
    public String getContents() { return contents; }
}
