package kr.codesqaud.cafe.domain;

import java.time.LocalDateTime;

public class Article {
    private Long id;
    private Long userFk;
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Article(Long id, Long userFk, String writer, String title, String contents, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userFk = userFk;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Article(Long userFk, String title, String contents) {
        this(null, userFk, null, title, contents, LocalDateTime.now(), LocalDateTime.now());
    }

    public Long getuserFk() {
        return userFk;
    }

    public Long getId() {
        return id;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
