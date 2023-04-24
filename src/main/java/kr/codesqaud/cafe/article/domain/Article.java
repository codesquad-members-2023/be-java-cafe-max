package kr.codesqaud.cafe.article.domain;

import java.time.LocalDateTime;
import java.util.Objects;


public class Article {

    private final String author;

    private final String title;

    private final String contents;

    private final LocalDateTime createdTime;

    private final Long id;


    public Article(String author, String title, String contents, Long id, LocalDateTime createdTime) {
        this.author = author;
        this.title = title;
        this.contents = contents;
        this.id = id;
        this.createdTime = Objects.requireNonNullElseGet(createdTime, LocalDateTime::now);
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public long getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

}
