package kr.codesqaud.cafe.dto.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import kr.codesqaud.cafe.domain.Comment;
import kr.codesqaud.cafe.dto.post.WriterResponse;

public class CommentResponse {

    private final Long id;
    private final Long postId;
    private final WriterResponse writer;
    private final String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime writeDate;

    public CommentResponse(Long id, Long postId, WriterResponse writer, String content,
        LocalDateTime writeDate) {
        this.id = id;
        this.postId = postId;
        this.writer = writer;
        this.content = content;
        this.writeDate = writeDate;
    }

    public static CommentResponse from(Comment comment) {
        return new CommentResponse(comment.getId(), comment.getPostId(), WriterResponse.from(comment.getWriter()),
            comment.getContent(), comment.getWriteDate());
    }

    public Long getId() {
        return id;
    }

    public Long getPostId() {
        return postId;
    }

    public WriterResponse getWriter() {
        return writer;
    }

    public String getContent() {
        return content;
    }

    public String getWriteDateFormat() {
        return writeDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public LocalDateTime getWriteDate() {
        return writeDate;
    }
}
