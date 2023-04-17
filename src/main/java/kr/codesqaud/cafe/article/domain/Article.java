package kr.codesqaud.cafe.article.domain;

import java.time.LocalDateTime;
import java.util.Objects;


public class Article {

    private String author;

    private String title;

    private String contents;

    private LocalDateTime time;

    private long id;


    public Article(String author, String title, String contents, Long id, LocalDateTime time) {
        this.author = author;
        this.title = title;
        this.contents = contents;
        if (id != null) this.id = id;
        this.time = Objects.requireNonNullElseGet(time, LocalDateTime::now);
    }

    public LocalDateTime getTime() {
        return time;
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
