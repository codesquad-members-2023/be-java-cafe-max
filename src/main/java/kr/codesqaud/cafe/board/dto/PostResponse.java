package kr.codesqaud.cafe.board.dto;

import kr.codesqaud.cafe.board.domain.BoardPost;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PostResponse {
    private Long postId;
    private String writerId;
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime writeDateTime;

    private PostResponse(Long postId, String writerId, String writer, String title, String contents, LocalDateTime writeDateTime) {
        this.postId = postId;
        this.writerId = writerId;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.writeDateTime = writeDateTime;
    }

    public BoardPost toEntity() {
        return BoardPost.builder()
                .postId(postId)
                .writer(writer)
                .title(title)
                .contents(contents)
                .build();
    }

    public static PostResponse from(BoardPost boardPost) {
        return new PostResponse(boardPost.getPostId(), boardPost.getWriterId(), boardPost.getWriter(), boardPost.getTitle(), boardPost.getContents(), boardPost.getWriteDateTime());
    }

    public Long getPostId() {
        return postId;
    }

    public String getWriterId() {
        return writerId;
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

    public String getWriteDateTime() {
        return writeDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
