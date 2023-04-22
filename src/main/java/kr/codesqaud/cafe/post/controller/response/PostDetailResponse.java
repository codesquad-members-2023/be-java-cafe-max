package kr.codesqaud.cafe.post.controller.response;

import kr.codesqaud.cafe.post.service.Post;
import kr.codesqaud.cafe.util.DateUtils;

import java.time.LocalDateTime;

public class PostDetailResponse {

    private final String writerId;
    private final String writerName;
    private final String title;
    private final String contents;
    private final String writingTime;

    private PostDetailResponse(String writerId, String writerName, String title, String contents, LocalDateTime writingTime) {
        this.writerId = writerId;
        this.writerName = writerName;
        this.title = title;
        this.contents = contents;
        this.writingTime = DateUtils.toStringDate(writingTime);
    }

    public static PostDetailResponse from(final Post post) {
        return new PostDetailResponse(post.getWriterId(), post.getWriterName(), post.getTitle(), post.getContents(), post.getWritingTime());
    }

    public String getWriterId() {
        return writerId;
    }

    public String getWriterName() {
        return writerName;
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
