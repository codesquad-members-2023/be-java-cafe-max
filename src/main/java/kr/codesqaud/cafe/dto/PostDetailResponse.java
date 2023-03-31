package kr.codesqaud.cafe.dto;

import java.time.LocalDateTime;

public class PostDetailResponse {

    private final String writer;
    private final String title;
    private final String contents;
    private final LocalDateTime writingTime;

    public PostDetailResponse(String writer, String title, String contents, LocalDateTime writingTime) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.writingTime = writingTime;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public LocalDateTime getWritingTime() {
        return writingTime;
    }
}
