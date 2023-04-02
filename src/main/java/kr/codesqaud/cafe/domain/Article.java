package kr.codesqaud.cafe.domain;

import java.time.LocalDateTime;

public class Article {
    private Long id;
    private String title;
    private String writer;
    private String contents;
    private final LocalDateTime createdAt;

    public Article() {
        this.createdAt = LocalDateTime.now();
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

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
