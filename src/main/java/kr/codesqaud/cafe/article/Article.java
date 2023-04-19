package kr.codesqaud.cafe.article;

/**
 * 게시글을 의미하는 객체
 */
public class Article {

    private long id;
    private String writer;
    private String title;
    private String contents;

    public Article(long id, String writer, String title, String contents) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public Article(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public Article(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

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
