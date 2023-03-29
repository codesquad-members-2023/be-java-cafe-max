package kr.codesqaud.cafe.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Article {

    String author;

    String title;

    String contents;

    LocalDateTime  time;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    long id;

    public Article(String author, String title, String contents) {
        this.author = author;
        this.title = title;
        this.contents = contents;
        this.time = LocalDateTime.now();

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
