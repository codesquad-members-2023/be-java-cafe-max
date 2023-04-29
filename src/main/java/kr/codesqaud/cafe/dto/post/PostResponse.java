package kr.codesqaud.cafe.dto.post;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import kr.codesqaud.cafe.domain.Post;

public class PostResponse {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final Long id;
    private final String title;
    private final String content;
    private final WriterResponse writer;
    private final LocalDateTime writeDate;
    private final Long views;

    public PostResponse(Long id, String title, String content, WriterResponse writer, LocalDateTime writeDate, Long views) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.writeDate = writeDate;
        this.views = views;
    }

    public static PostResponse of(Post post, WriterResponse writerResponse) {
        return new PostResponse(post.getid(), post.getTitle(), post.getContent(), writerResponse, post.getWriteDate(), post.getViews());
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public WriterResponse getWriter() {
        return writer;
    }

    public LocalDateTime getWriteDate() {
        return writeDate;
    }

    public Long getViews() {
        return views;
    }

    public String getWriterEmail() {
        return writer.getWriterEmail();
    }

    public String convertLocalDateTime() {
        return writeDate.format(FORMATTER);
    }
}
