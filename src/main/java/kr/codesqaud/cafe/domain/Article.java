package kr.codesqaud.cafe.domain;

/**
 * 게시글을 의미하는 객체
 */
public class Article {

    private long index;
    private String writer;
    private String title;
    private String contents;

    public long getIndex() {
        return index;
    }

    public void setIndex(final long index) {
        this.index = index;
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
