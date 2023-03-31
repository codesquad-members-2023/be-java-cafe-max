package kr.codesqaud.cafe.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PostListResponse {

    private final long id;
    private final String writer;
    private final String title;
    private final String writingTime;

    public PostListResponse(long id, String writer, String title, LocalDateTime writingTime) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.writingTime = writingTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
