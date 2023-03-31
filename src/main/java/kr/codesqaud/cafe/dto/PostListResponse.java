package kr.codesqaud.cafe.dto;

import java.time.LocalDateTime;

public class PostListResponse {

    private final long id;
    private final String writer;
    private final String title;
    private final LocalDateTime writingTime;

    public PostListResponse(long id, String writer, String title, LocalDateTime writingTime) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.writingTime = writingTime;
    }
}
