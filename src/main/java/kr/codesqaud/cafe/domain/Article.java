package kr.codesqaud.cafe.domain;

import java.time.LocalDateTime;


public class Article {

    private String author;

    private String title;

    private String contents;

    private LocalDateTime time;

    private long id;

    public Article(String author, String title, String contents) {
        this.author = author;
        this.title = title;
        this.contents = contents;
        this.time = LocalDateTime.now();
    }

    public LocalDateTime getTime() {
        return time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
