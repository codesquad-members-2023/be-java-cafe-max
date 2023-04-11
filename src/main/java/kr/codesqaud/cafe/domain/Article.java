package kr.codesqaud.cafe.domain;

import java.time.LocalDateTime;

public class Article {
    private Integer id;
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime writeTime;

    public Article(final String writer, final String title, final String contents, final LocalDateTime writeTime) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.writeTime = writeTime;
    }

    public Article(final Integer id, final String writer, final String title, final String contents, final LocalDateTime writeTime) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.writeTime = writeTime;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getWriteTime() {
        return writeTime;
    }

    public String getContents() {
        return contents;
    }
}
