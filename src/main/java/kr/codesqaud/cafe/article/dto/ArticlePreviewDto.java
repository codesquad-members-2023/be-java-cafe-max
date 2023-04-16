package kr.codesqaud.cafe.article.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ArticlePreviewDto {
    //글 목록에 표시할 내용 DTO
    private String author;

    private String title;

    private String time;

    private long id;

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    public long getId() {
        return id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTime(LocalDateTime time) {
        this.time = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public void setId(long id) {
        this.id = id;
    }
}
