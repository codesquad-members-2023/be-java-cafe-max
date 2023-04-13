package kr.codesqaud.cafe.article.domain;


import java.sql.Timestamp;
import java.time.LocalDateTime;


public class Article {

    private String author;

    private String title;

    private String contents;

    private LocalDateTime time = LocalDateTime.now();

    private long id;



    public Article(String author, String title, String contents, long id, Timestamp time) {
        this.author = author;
        this.title = title;
        this.contents = contents;
        this.id = id;
        this.time = time.toLocalDateTime();
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
