package kr.codesqaud.cafe.dto.post;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import kr.codesqaud.cafe.domain.Post;

public class PostResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final WhiterResponse writer;
    private final LocalDateTime writeDate;
    private final Long views;

    public PostResponse(Long id, String title, String content, WhiterResponse writer,
        LocalDateTime writeDate, Long views) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.writeDate = writeDate;
        this.views = views;
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

    public WhiterResponse getWriter() {
        return writer;
    }

    public String getWriteDateFormat() {
        return writeDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public LocalDateTime getWriteDate() {
        return writeDate;
    }

    public Long getViews() {
        return views;
    }

    public static PostResponse of(Post post, WhiterResponse writer) {
        return new PostResponse(post.getId(), post.getTitle(), post.getContent(),
            writer, post.getWriteDate(), post.getViews());
    }
}
