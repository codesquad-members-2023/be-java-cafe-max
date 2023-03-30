package kr.codesqaud.cafe.domain;

public class Article {
    private Long index;
    private String title;
    private String writer;
    private String contents;
    private String writeDate;
    private Long hits = 0L;

    public Article(Long index, String title, String writer, String contents, String writeDate) {
        this.index = index;
        this.title = title;
        this.writer = writer;
        this.contents = contents;
        this.writeDate = writeDate;
    }

    public String getTitle() {
        return title;
    }

    public String getWriter() {
        return writer;
    }

    public String getContents() {
        return contents;
    }

    public String getWriteDate() {
        return writeDate;
    }

    public Long getHits() {
        return hits;
    }

    public void setHits(Long hits) {
        this.hits = hits;
    }
}
