package kr.codesqaud.cafe.domain;

import java.time.LocalDateTime;

public class Article {
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime writeTime;

    public Article(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getWriteTime() {
        return writeTime;
    }

    public String getContents() {
        return contents;
    }
}
