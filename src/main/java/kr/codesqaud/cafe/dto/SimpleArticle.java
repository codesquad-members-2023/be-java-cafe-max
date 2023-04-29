package kr.codesqaud.cafe.dto;

import java.time.LocalDateTime;

public class SimpleArticle {

    private String writer;
    private String userId;
    private String title;
    private Long id;
    private LocalDateTime createdTime;

    public SimpleArticle() {}

    public SimpleArticle(String writer, String userId, String title, Long id, LocalDateTime createdTime) {
        this.writer = writer;
        this.userId = userId;
        this.title = title;
        this.id = id;
        this.createdTime = createdTime;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }
}
