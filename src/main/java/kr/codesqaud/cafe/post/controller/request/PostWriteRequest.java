package kr.codesqaud.cafe.post.controller.request;

import kr.codesqaud.cafe.post.service.Post;

public class PostWriteRequest {

    private final String writer;
    private final String title;
    private final String contents;

    public PostWriteRequest(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public Post toEntity(long id) {
        return new Post(id, writer, title, contents);
    }
}
