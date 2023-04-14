package kr.codesqaud.cafe.article;

/**
 * 게시글을 의미하는 객체
 */
public class Article {

    private long sequence;
    private String writer;
    private String title;
    private String contents;

    public Article(long sequence, String writer, String title, String contents) {
        this.sequence = sequence;
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

    public long getSequence() {
        return sequence;
    }

    public void setSequence(final long sequence) {
        this.sequence = sequence;
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
