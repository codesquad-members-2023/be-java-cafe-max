package kr.codesqaud.cafe.post.controller.response;

import kr.codesqaud.cafe.post.service.Post;
import kr.codesqaud.cafe.util.DateUtils;

import java.time.LocalDateTime;

public class PostDetailResponse {

    private final String writer;
    private final String title;
    private final String contents;
    private final String writingTime;

    private PostDetailResponse(String writer, String title, String contents, LocalDateTime writingTime) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.writingTime = DateUtils.toStringDate(writingTime);
    }

    public static PostDetailResponse from(final Post post) {
        return new PostDetailResponse(post.getWriter(), post.getTitle(), post.getContents(), post.getWritingTime());
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

    public String getWritingTime() {
        return writingTime;
    }
}
