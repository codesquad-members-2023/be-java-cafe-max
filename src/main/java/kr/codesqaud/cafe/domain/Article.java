package kr.codesqaud.cafe.domain;

import java.time.LocalDateTime;

public class Article {

    private String writer;
    private String userId;
    private String title;
    private String contents;

    private Long id;

    private LocalDateTime createdTime;

    public Article(String userId, String writer, String title, String contents) {
        this.userId = userId;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdTime = LocalDateTime.now();
    }

    public Article(String userId, String writer, String title, String contents, LocalDateTime createdTime) {
        this.userId = userId;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdTime = createdTime;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isAuthor(String userId) {
        return this.userId.equals(userId);
    }
}
