package kr.codesqaud.cafe.dto.reply;

import kr.codesqaud.cafe.domain.Reply;

import java.time.LocalDateTime;

public class ReplyResponse {
    private long id;
    private long articleId;
    private String userId;
    private String contents;
    private LocalDateTime createdAt;

    private ReplyResponse(final long id, final String userId, final String contents, final LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.contents = contents;
        this.createdAt = createdAt;
    }

    public static ReplyResponse from(Reply reply) { // Entity → DTO
        return new ReplyResponse(reply.getId(), reply.getUserId(), reply.getContents(), reply.getCreatedAt());
    }

    public long getId() {
        return id;
    }
    public long getArticleId() {
        return articleId;
    }
    public String getUserId() {
        return userId;
    }
    public String getContents() {
        return contents;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
