package kr.codesqaud.cafe.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Article {
    private long id;
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime createdTime;

    public Article(){}
    public Article(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdTime = LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE)); // this.createdTime = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) { this.writer = writer; }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) { this.contents = contents; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

//    public LocalDateTime getCreatedTime() { return createdTime; }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }
}
