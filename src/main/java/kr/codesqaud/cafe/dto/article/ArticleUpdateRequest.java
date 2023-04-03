package kr.codesqaud.cafe.dto.article;

public class ArticleUpdateRequest {
    private Long id;
    private String title;
    private String writer;
    private String contents;

    public ArticleUpdateRequest(Long id, String title, String writer, String contents) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.contents = contents;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getWriter() { return writer; }
    public String getContents() { return contents; }
}
