package kr.codesqaud.cafe.controller.dto.article;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ArticleTimeForm {
    private Long id;
    private String writer;
    private String title;
    private String contents;
    private String currentTime;

    public ArticleTimeForm() {
    }

    public ArticleTimeForm(Long id, String writer, String title, String contents, LocalDateTime currentTime) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.currentTime = currentTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
