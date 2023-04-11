package kr.codesqaud.cafe.article.domain;

import java.sql.Timestamp;
import java.time.LocalDateTime;


public class Article {

    private String author;

    private String title;

    private String contents;

    private LocalDateTime time = LocalDateTime.now();

    private long id;

    public Article() {
    }

    public void setTime(Timestamp time) {
        this.time = time.toLocalDateTime();
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

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
