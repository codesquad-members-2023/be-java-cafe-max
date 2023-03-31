package kr.codesqaud.cafe.domain;

import kr.codesqaud.cafe.dto.PostListResponse;

import java.time.LocalDateTime;

public class Post {

    private final long id;
    private final String writer;
    private final String title;
    private final String contents;
    private final LocalDateTime writingTime;

    public Post(long id, String writer, String title, String contents) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.writingTime = LocalDateTime.now();
    }

    public PostListResponse toListResponse() {
        return new PostListResponse(id, writer, title, writingTime);
    }
}
