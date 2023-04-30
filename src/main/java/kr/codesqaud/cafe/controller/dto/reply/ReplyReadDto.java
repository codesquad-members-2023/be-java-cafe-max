package kr.codesqaud.cafe.controller.dto.reply;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.codesqaud.cafe.domain.Reply;

import java.time.LocalDateTime;

public class ReplyReadDto {
    private final Long id;
    private final Long userId;
    private final String writer;
    private final String comments;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm")
    private final LocalDateTime createdAt;

    public ReplyReadDto(Long id, Long userId, String writer, String comments, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.writer = writer;
        this.comments = comments;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getWriter() {
        return writer;
    }

    public String getComments() {
        return comments;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public static ReplyReadDto from(Reply reply) {
        return new ReplyReadDto(reply.getId(), reply.getUserId(), reply.getWriter(), reply.getComments(), reply.getCreatedAt());
    }
}
