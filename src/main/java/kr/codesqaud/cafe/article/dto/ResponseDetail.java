package kr.codesqaud.cafe.article.dto;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ResponseDetail {
    //세부적인 글 내용 DTO
    private final String author;

    private final String title;

    private final String contents;

    private final String time;

    private final long id;

    public ResponseDetail(String author, String title, String contents, LocalDateTime time, long id) {
        this.author = author;
        this.title = title;
        this.contents = contents;
        this.time = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.id = id;
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

    public String getTime() {
        return time;
    }

}
