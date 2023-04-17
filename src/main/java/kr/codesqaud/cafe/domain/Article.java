package kr.codesqaud.cafe.domain;

import java.time.LocalDateTime;

public class Article {
    private Long id;
    private String title;
    private String writer;
    private String contents;
    private LocalDateTime createdAt;
    private Long previousId;
    private Long nextId;

    public Article() {

    }

    public Article(String title, String writer, String contents) {
        this.title = title;
        this.writer = writer;
        this.contents = contents;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }
    public String getTitle() { return title; }
    public String getWriter() { return writer; }
    public String getContents() { return contents; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public Long getPreviousId() {
        return previousId;
    }
    public Long getNextId() {
        return nextId;
    }

    public void setId(Long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setWriter(String writer) { this.writer = writer; }
    public void setContents(String contents) { this.contents = contents; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setPreviousId(final Long previousId) {
        this.previousId = previousId;
    }
    public void setNextId(final Long nextId) {
        this.nextId = nextId;
    }
}
