package kr.codesqaud.cafe.article.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ResponsePreview {
    //글 목록에 표시할 내용 DTO
    private final String author;

    private final String title;

    private final String createdTime;

    private final long id;

    public ResponsePreview(String author, String title, LocalDateTime createdTime, long id) {
        this.author = author;
        this.title = title;
        this.createdTime = createdTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }


    public String getTitle() {
        return title;
    }


    public String getCreatedTime() {
        return createdTime;
    }


    public long getId() {
        return id;
    }


}
