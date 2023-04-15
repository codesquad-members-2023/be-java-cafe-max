package kr.codesqaud.cafe.post.controller.request;

import kr.codesqaud.cafe.post.service.Post;

import java.time.LocalDateTime;

public class PostWriteRequest {

    private final String writer;
    private final String title;
    private final String contents;

    public PostWriteRequest(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public Post toPost() {
        return new Post(null, writer, title, contents, LocalDateTime.now());
    }
}
