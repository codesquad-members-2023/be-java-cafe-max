package kr.codesqaud.cafe.domain;

import java.time.LocalTime;

public class Article {
    private String writer;
    private String title;
    private String contents;
    private LocalTime writtenTime;

    public Article(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.writtenTime = LocalTime.now();
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public LocalTime getWrittenTime() {
        return writtenTime;
    }

    public void setWrittenTime(LocalTime writtenTime) {
        this.writtenTime = writtenTime;
    }
}
