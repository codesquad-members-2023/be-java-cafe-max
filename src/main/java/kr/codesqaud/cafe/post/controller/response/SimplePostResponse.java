package kr.codesqaud.cafe.post.controller.response;

import kr.codesqaud.cafe.post.service.Post;

public class SimplePostResponse {

    private final String writerId;
    private final String title;
    private final String contents;

    private SimplePostResponse(String writerId, String title, String contents) {
        this.writerId = writerId;
        this.title = title;
        this.contents = contents;
    }

    public static SimplePostResponse from(final Post post) {
        return new SimplePostResponse(post.getWriterId(), post.getTitle(), post.getContents());
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }
}
