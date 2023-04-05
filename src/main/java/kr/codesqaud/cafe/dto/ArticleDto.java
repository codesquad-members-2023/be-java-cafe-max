package kr.codesqaud.cafe.dto;

public class ArticleDto {
    private String title;
    private String writer;
    private String contents;

    public String getTitle() {
        return title;
    }

    public boolean setTitle(String title) {
        this.title = title;
        return true;
    }

    public String getWriter() {
        return writer;
    }

    public boolean setWriter(String writer) {
        this.writer = writer;
        return true;
    }

    public String getContents() {
        return contents;
    }

    public boolean setContents(String contents) {
        this.contents = contents;
        return true;
    }
}
