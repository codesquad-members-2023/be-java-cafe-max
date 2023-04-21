package kr.codesqaud.cafe.post.controller.response;

import kr.codesqaud.cafe.post.service.Post;

public class SimplePostResponse {

    private final String title;
    private final String contents;

    private SimplePostResponse(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public static SimplePostResponse from(final Post post) {
        return new SimplePostResponse(post.getTitle(), post.getContents());
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }
}
