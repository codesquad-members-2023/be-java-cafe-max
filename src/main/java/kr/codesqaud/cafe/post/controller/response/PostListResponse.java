package kr.codesqaud.cafe.post.controller.response;

import kr.codesqaud.cafe.post.service.Post;
import kr.codesqaud.cafe.util.DateUtils;

import java.time.LocalDateTime;

public class PostListResponse {

    private final long id;
    private final String writer_id;
    private final String writer_name;
    private final String title;
    private final String writingTime;

    private PostListResponse(long id, String writer_id, String writer_name, String title, LocalDateTime writingTime) {
        this.id = id;
        this.writer_id = writer_id;
        this.writer_name = writer_name;
        this.title = title;
        this.writingTime = DateUtils.toStringDate(writingTime);
    }

    public static PostListResponse from(final Post post) {
        return new PostListResponse(
                post.getId(),
                post.getWriterId(),
                post.getWriterName(),
                post.getTitle(),
                post.getWritingTime()
        );
    }
}
