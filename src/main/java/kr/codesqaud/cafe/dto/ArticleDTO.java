package kr.codesqaud.cafe.dto;

/**
 * 게시글 DTO 객체
 */
public class ArticleDTO {

    private String writer;
    private String title;
    private String contents;

    public String getWriter() {
        return writer;
    }

    public void setWriter(final String writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(final String contents) {
        this.contents = contents;
    }
}
