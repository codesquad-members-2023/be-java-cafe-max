package kr.codesqaud.cafe.domain;

import java.time.LocalDateTime;

public class Reply {
    private Long id;
    private Long articleId;
    private Long userId;
    private String writer;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Reply(Long id, Long articleId, Long userId, String writer, String comment, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.articleId = articleId;
        this.userId = userId;
        this.writer = writer;
        this.comment = comment;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
