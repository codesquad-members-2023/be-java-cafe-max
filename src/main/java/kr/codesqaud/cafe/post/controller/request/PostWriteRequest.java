package kr.codesqaud.cafe.post.controller.request;

import kr.codesqaud.cafe.post.service.Post;
import kr.codesqaud.cafe.user.service.User;

import java.time.LocalDateTime;

public class PostWriteRequest {

    private final String title;
    private final String contents;

    public PostWriteRequest(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public Post toPost(User writer) {
        return new Post(null, writer.getUserId(), writer.getName(), title, contents, LocalDateTime.now());
    }
}
