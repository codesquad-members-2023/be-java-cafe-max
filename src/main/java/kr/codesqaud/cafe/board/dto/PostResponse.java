package kr.codesqaud.cafe.board.dto;

import kr.codesqaud.cafe.board.domain.BoardPost;
import kr.codesqaud.cafe.user.domain.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PostResponse {
    private Long postId;
    private String title;
    private String contents;
    private LocalDateTime writeDateTime;
    private User writer;

    private PostResponse(Long postId, String title, String contents, LocalDateTime writeDateTime, User writer) {
        this.postId = postId;
        this.title = title;
        this.contents = contents;
        this.writeDateTime = writeDateTime;
        this.writer = writer;
    }

    public BoardPost toEntity() {
        return BoardPost.builder()
                .postId(postId)
                .title(title)
                .contents(contents)
                .writer(writer)
                .build();
    }

    public static PostResponse from(BoardPost boardPost) {
        return new PostResponse(boardPost.getPostId(), boardPost.getTitle(), boardPost.getContents(), boardPost.getWriteDateTime(), boardPost.getWriter());
    }

    public Long getPostId() {
        return postId;
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

    public User getWriter() {
        return writer;
    }
}
