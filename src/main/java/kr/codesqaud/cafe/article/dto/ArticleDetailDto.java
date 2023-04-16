package kr.codesqaud.cafe.article.dto;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ArticleDetailDto {
    //세부적인 글 내용 DTO
    private String author;

    private String title;

    private String contents;

    private String time;

    private long id;

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

    public String getTime() {
        return time;
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

    public void setTime(LocalDateTime time) {
        this.time = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }


}
